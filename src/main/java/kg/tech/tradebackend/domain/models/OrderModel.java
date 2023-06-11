package kg.tech.tradebackend.domain.models;

import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.enums.PaymentMethod;
import kg.tech.tradebackend.domain.enums.Transport;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderModel {
    Long id;

    OrderStatus status = OrderStatus.START;

    BigDecimal total;

    LocalDateTime createdAt = LocalDateTime.now();
    LocalDate deliverDate;

    List<ItemModel> items;
    PaymentMethod paymentMethod;
    Transport transport;
    Long userId;
    String history;

    public String toEmailString() {
        StringJoiner emailText = new StringJoiner("\n");

        return emailText
                .add("Информация о заказе:")
                .add("[")
                .add("Статус: " + status.statusDescription())
                .add("Создано: " + createdAt)
                .add("Дата доставки: " + deliverDate)
                .add("Товары: " + items.toString())
                .add("Общая сумма: " + total)
                .add("]")
                .toString();
    }
}
