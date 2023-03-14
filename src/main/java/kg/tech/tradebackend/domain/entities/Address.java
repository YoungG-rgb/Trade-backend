package kg.tech.tradebackend.domain.entities;

import kg.tech.tradebackend.domain.enums.HouseType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
    @SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ", allocationSize = 1)
    Long id;

    String town;

    String street;

    String houseNumber;

    @Enumerated(EnumType.STRING)
    HouseType houseType;
}
