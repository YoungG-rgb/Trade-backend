package kg.tech.tradebackend.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
    @SequenceGenerator(name = "USERS_SEQ", sequenceName = "USERS_SEQ", allocationSize = 1)
    Long id;

    String username;

    String password;

    String email;

    BigDecimal balance;

    LocalDate updatedAt;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_orders",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "orders_id")})
    List<Order> orders;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    Address address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_coupons",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "coupon_id")})
    List<Coupon> coupons;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_phones",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "phones_id")})
    List<Phone> phones;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    Card creditCard;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    Cart cart;
}
