package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.enums.Transport;
import kg.tech.tradebackend.domain.models.OrderModel;
import kg.tech.tradebackend.services.TaxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class TaxServiceImpl implements TaxService {

    @Override
    public void apply(OrderModel orderModel) {
        orderModel.setTotal(orderModel.getTotal().add(BigDecimal.TEN));
    }

    @Override
    public void apply(Order order, Transport transport) {
        if (transport.equals(Transport.PLANE)) {
            order.setHistory(order.getHistory() + "<br> Учет перевозки на самолете: " + 499 + " с");
            order.setTotal(order.getTotal().add(BigDecimal.valueOf(499)));
        } else {
            order.setHistory(order.getHistory() + "<br> Учет перевозки на машине: " + 0 + " с");
        }

        BigDecimal taxed = collect(order.getTotal());
        order.setHistory(order.getHistory() + "<br> Налог составил: " + taxed + " с");
        order.setTotal(order.getTotal().add(taxed));
    }


    private BigDecimal collect(BigDecimal total) {
        return BigDecimal.TEN;
    }
}
