package kg.tech.tradebackend.domain.models;

import kg.tech.tradebackend.domain.entities.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartModel {
    Long id;

    Long userId;

    BigDecimal totalAmount;

    BigDecimal totalBonuses;

    List<SimpleOrderView> simpleOrderViews = new ArrayList<>();

    public void buildFromOrders(List<Order> orders) {
        List<SimpleOrderView> fromOrders = Collections.emptyList();

        if (!orders.isEmpty()) {
            fromOrders = orders.stream()
                    .map(order -> SimpleOrderView.builder()
                            .createdAt(order.getCreatedAt())
                            .totalAmount(order.getTotal())
                            .paymentMethod(order.getPaymentMethod())
                            .deliverDate(order.getDeliverDate())
                            .build()
                    )
                    .toList();
        }

        this.simpleOrderViews.addAll(fromOrders);
    }

}
