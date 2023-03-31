package kg.tech.tradebackend.services.impl;

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
}
