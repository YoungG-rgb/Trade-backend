package kg.tech.tradebackend.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupons")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COUPONS_SEQ")
    @SequenceGenerator(name = "COUPONS_SEQ", sequenceName = "COUPONS_SEQ", allocationSize = 1)
    Long id;

    String uuid;

    @Column(columnDefinition = "boolean default true")
    boolean isValid;

    BigDecimal bonus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    User user;
}
