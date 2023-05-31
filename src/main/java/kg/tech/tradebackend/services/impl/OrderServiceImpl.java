package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.*;
import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.repositories.ItemRepository;
import kg.tech.tradebackend.utils.BaseValidator;
import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.models.OrderModel;
import kg.tech.tradebackend.mappers.OrderMapper;
import kg.tech.tradebackend.repositories.CouponRepository;
import kg.tech.tradebackend.repositories.OrderRepository;
import kg.tech.tradebackend.repositories.UserRepository;
import kg.tech.tradebackend.services.EmailSenderService;
import kg.tech.tradebackend.services.OrderService;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;
    EmailSenderService emailSenderService;
    CouponRepository couponRepository;
    UserRepository userRepository;

    @Override
    public OrderModel save(OrderModel orderModel) throws Exception {
        validateOrder(orderModel);
        emailSenderService.send(orderModel.getUserId(), "ORDER", orderModel.toEmailString());
        orderRepository.save(orderMapper.toEntity(orderModel));
        return orderModel;
    }

    private void subtractAndToInvalidity(OrderModel orderModel, List<Long> coupons) {
        List<Coupon> couponsEntity = couponRepository.findAllByIdInAndValidIsTrue(coupons);
        BigDecimal subtractedTotal = orderModel.getTotal().subtract(applyCoupons(couponsEntity));
        orderModel.setTotal(subtractedTotal);

        couponsEntity.forEach(coupon -> coupon.setValid(false));
        couponRepository.saveAll(couponsEntity);
    }

    @Override
    public OrderModel update(OrderModel orderModel) throws OrderException {
        if (orderModel.getId() == null) throw new OrderException("ORDER_NOT_FOUND");
        orderRepository.save(orderMapper.toEntity(orderModel));
        return orderModel;
    }

    @Override
    public void delete(Long orderId) throws OrderException {
        orderRepository.delete(
                orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderException("ORDER_NOT_FOUND"))
        );
    }

    @Override
    public void changeStatusToSent(Long orderId) throws OrderException {
        Order order = this.findById(orderId);
        order.setStatus(OrderStatus.SENT);
        orderRepository.save(order);
    }

    @Override
    public void changeStatusToDelivered(Long orderId) throws OrderException {
        Order order = this.findById(orderId);
        order.setStatus(OrderStatus.DELIVERED);
        orderRepository.save(order);
    }

    @Override
    public void changeStatusToCompleted(Long orderId) throws OrderException {
        Order order = this.findById(orderId);
        order.setStatus(OrderStatus.COMPLETE);
        orderRepository.save(order);
    }

    @Override
    public OrderModel findByUserIdAndStatus(Long userId, OrderStatus status) {
        return orderMapper.toModel(
                orderRepository.findByUserIdAndStatusIs(userId, status)
        );
    }

    private Order findById(Long id) throws OrderException {
        return orderRepository
                .findById(id)
                .orElseThrow(() -> new OrderException("ORDER_NOT_FOUND"));
    }

    private BigDecimal applyCoupons(List<Coupon> coupons) {
        return coupons
                .stream()
                .map(Coupon::getBonus)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private void validateOrder(OrderModel orderModel) throws OrderException {
        User user = userRepository.findById(orderModel.getUserId()).orElseThrow(() -> new OrderException("Пользователя с таким id нет"));
        Card creditCard = user.getCreditCard();

        if (user.getAddress() == null) throw new OrderException("Пожалуйста, укажите адрес");

        if (switch(orderModel.getPaymentMethod()) {
            case BALANCE -> user.getBalance().compareTo(orderModel.getTotal()) < 0;
            case INSTALLMENTS -> false;
            case CREDIT_CARD ->
                    BaseValidator.isEmpty( creditCard.getCardNumber(), creditCard.getCvcAndCvv() ) && creditCard.getExpiryDate() == null;
        }) throw new OrderException("Не хватает средств");
    }
}
