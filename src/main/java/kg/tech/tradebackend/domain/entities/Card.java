package kg.tech.tradebackend.domain.entities;

import kg.tech.tradebackend.domain.models.CardModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARDS_SEQ")
    @SequenceGenerator(name = "CARDS_SEQ", sequenceName = "CARDS_SEQ", allocationSize = 1)
    Long id;

    String cardNumber;

    LocalDate expiryDate;

    String cvcAndCvv;

    public Card(CardModel cardModel) {
        this.id = cardModel.getId();
        this.cardNumber = cardModel.getCardNumber();
        this.expiryDate = cardModel.getExpiryDate();
        this.cvcAndCvv = cardModel.getCvcAndCvv();
    }
}
