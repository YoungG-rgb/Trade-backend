package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.filterPatterns.OrderFilterPattern;
import kg.tech.tradebackend.domain.models.OrderModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    OrderModel save(OrderModel orderModel) throws Exception;
    OrderModel update(OrderModel orderModel) throws OrderException;
    void delete(Long orderId) throws OrderException;
    void changeStatusToSent(Long orderId) throws OrderException;
    void changeStatusToDelivered(Long orderId) throws OrderException;
    void changeStatusToCompleted(Long orderId) throws OrderException;

    OrderModel findByUserIdAndStatus(Long userId, OrderStatus status);

    Page<OrderModel> findByUsername(String username, OrderFilterPattern orderFilterPattern) throws OrderException;

}
