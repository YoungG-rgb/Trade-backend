package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.models.OrderModel;

import java.util.List;

public interface OrderService {
    OrderModel save(OrderModel orderModel, List<Long> applyCoupons) throws Exception;
    OrderModel update(OrderModel orderModel) throws OrderException;
    void delete(Long orderId) throws OrderException;
    void changeStatusToSent(Long orderId) throws OrderException;
    void changeStatusToDelivered(Long orderId) throws OrderException;
    void changeStatusToCompleted(Long orderId) throws OrderException;

    List<OrderModel> findAllByUserId(Long userId);


}
