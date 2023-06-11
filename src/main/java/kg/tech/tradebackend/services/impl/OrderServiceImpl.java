package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.*;
import kg.tech.tradebackend.domain.enums.PaymentMethod;
import kg.tech.tradebackend.domain.enums.Transport;
import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.OrderFilterPattern;
import kg.tech.tradebackend.domain.models.AddressModel;
import kg.tech.tradebackend.domain.models.OrderReport;
import kg.tech.tradebackend.domain.models.RequestOrderModel;
import kg.tech.tradebackend.mappers.AddressMapper;
import kg.tech.tradebackend.repositories.ItemRepository;
import kg.tech.tradebackend.services.TaxService;
import kg.tech.tradebackend.specifications.ItemSpecification;
import kg.tech.tradebackend.specifications.OrderSpecification;
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
import kg.tech.tradebackend.utils.excel.ExcelUtils;
import kg.tech.tradebackend.utils.excel.WorkbookBuilder;
import kg.tech.tradebackend.utils.excel.WorkbookReference;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;
    TaxService taxService;
    EmailSenderService emailSenderService;
    UserRepository userRepository;
    AddressMapper addressMapper;

    @Override
    public OrderModel save(OrderModel orderModel) throws Exception {
        validateAndSubtract(orderModel.getUserId(), orderModel.getPaymentMethod(), orderModel.getTotal());

        orderRepository.save(orderMapper.toEntity(orderModel));
        return orderModel;
    }

    @Override
    public String apply(RequestOrderModel requestOrderModel) throws Exception {
        Order order = orderRepository.findById(requestOrderModel.getOrderId()).orElseThrow();
        order.setTransport(Transport.valueOf(requestOrderModel.getTransport()));
        order.setPaymentMethod(PaymentMethod.valueOf(requestOrderModel.getPaymentMethod()));
        taxService.apply(order, order.getTransport());

        String payInfo = validateAndSubtract(requestOrderModel.getUserId(), order.getPaymentMethod(), order.getTotal());
        emailSenderService.send(requestOrderModel.getUserId(), "ORDER", order.toEmailString(payInfo));
        order.setStatus(OrderStatus.PACKAGED);
        orderRepository.save(order);

        return "SUCCESS";
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

    private String validateAndSubtract(Long userId, PaymentMethod paymentMethod, BigDecimal total) throws OrderException {
        User user = userRepository.findById(userId).orElseThrow(() -> new OrderException("Пользователя с таким id нет"));
        Card creditCard = user.getCreditCard();

        if (user.getAddress() == null || user.getAddress().getLongitude() == null
        && user.getAddress().getLatitude() == null) throw new OrderException("Пожалуйста, укажите адрес");


        if (switch(paymentMethod) {
            case BALANCE -> user.getBalance().compareTo(total) < 0;
            case INSTALLMENTS -> false;
            case CREDIT_CARD -> BaseValidator.isEmpty( creditCard.getCardNumber(), creditCard.getCvcAndCvv() ) && creditCard.getExpiryDate() == null;
        }) throw new OrderException("Не хватает средств");
        else {
            return switch (paymentMethod) {
                case BALANCE -> {
                    BigDecimal subtractedBalance = user.getBalance().subtract(total);
                    user.setBalance(subtractedBalance);
                    userRepository.save(user);
                    yield "Успешно произведена оплата с баланса";
                }
                case INSTALLMENTS -> String.format("Ваш код %s для оформления рассрочки", UUID.randomUUID());
                case CREDIT_CARD -> "Успешно произведена оплата с карты";
            };
        }

    }

    @Override
    public Page<OrderModel> findByUsername(String username, OrderFilterPattern paginationCriteria) throws OrderException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new OrderException("Пользователя с таким именем нет"));
        OrderSpecification orderSpecification = new OrderSpecification(paginationCriteria, user);
        Page<Order> orderPage = orderRepository.findAll(orderSpecification, paginationCriteria.toPageRequest());

        return new PageImpl<>(
                orderPage.stream().map(orderMapper::toModel).toList(),
                paginationCriteria.toPageRequest(),
                orderPage.getTotalPages()
        );
    }

    @Override
    public AddressModel loadMapByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        return addressMapper.toModel(order.getUser().getAddress());
    }

    @Override
    public ByteArrayOutputStream exportOrderReport(Long userId) {
        List<OrderReport> orderReports = orderRepository.findByUserId(userId).stream().map(OrderReport::new).toList();

        WorkbookReference workbookRef = new WorkbookBuilder()
                .sheet()
                .title("Отчет по рекламе O! Store")
                .from(orderReports)
                .endSheet()
                .build();

        Workbook workbook = workbookRef.toWorkbook();
        ExcelUtils.autoSizeAllSheets(workbook);
        return ExcelUtils.workbookToResource(workbook);
    }
}
