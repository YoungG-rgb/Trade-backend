package kg.tech.tradebackend.domain.entities;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "phones")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PHONES_SEQ")
    @SequenceGenerator(name = "PHONES_SEQ", sequenceName = "PHONES_SEQ", allocationSize = 1)
    Long id;


    String number;

}
