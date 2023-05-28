package kg.tech.tradebackend.domain.entities;

import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.enums.PaymentMethod;
import kg.tech.tradebackend.domain.enums.Transport;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ")
    @SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "ORDERS_SEQ", allocationSize = 1)
    Long id;

    @Enumerated(EnumType.STRING)
    OrderStatus status;

    BigDecimal total = BigDecimal.ZERO;

    LocalDateTime createdAt;
    LocalDate deliverDate;

    @Enumerated(value = EnumType.STRING)
    Transport transport;

    @Enumerated(value = EnumType.STRING)
    PaymentMethod paymentMethod;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "orders_item",
            joinColumns = {@JoinColumn(name = "orders_id")},
            inverseJoinColumns = {@JoinColumn(name = "item_id")})
    List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void addToTotal(BigDecimal price) {
        this.total.add(price);
    }
}
