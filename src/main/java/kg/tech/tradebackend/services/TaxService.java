package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.enums.Transport;
import kg.tech.tradebackend.domain.models.OrderModel;

public interface TaxService {
    void apply(OrderModel orderModel);
    void apply(Order order, Transport transport);
}
