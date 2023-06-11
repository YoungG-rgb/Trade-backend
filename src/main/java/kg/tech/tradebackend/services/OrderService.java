package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.filterPatterns.OrderFilterPattern;
import kg.tech.tradebackend.domain.models.AddressModel;
import kg.tech.tradebackend.domain.models.OrderModel;
import kg.tech.tradebackend.domain.models.RequestOrderModel;
import org.springframework.data.domain.Page;

import java.io.ByteArrayOutputStream;

public interface OrderService {
    OrderModel save(OrderModel orderModel) throws Exception;
    String apply(RequestOrderModel requestOrderModel) throws Exception;
    OrderModel update(OrderModel orderModel) throws OrderException;
    void delete(Long orderId) throws OrderException;
    void changeStatusToSent(Long orderId) throws OrderException;
    void changeStatusToDelivered(Long orderId) throws OrderException;
    void changeStatusToCompleted(Long orderId) throws OrderException;

    OrderModel findByUserIdAndStatus(Long userId, OrderStatus status);

    Page<OrderModel> findByUsername(String username, OrderFilterPattern orderFilterPattern) throws OrderException;

    AddressModel loadMapByOrderId(Long orderId);

    ByteArrayOutputStream exportOrderReport(Long userId);
}
