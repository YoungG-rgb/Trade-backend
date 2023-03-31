package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.models.OrderModel;

public interface TaxService {
    void apply(OrderModel orderModel);
}
