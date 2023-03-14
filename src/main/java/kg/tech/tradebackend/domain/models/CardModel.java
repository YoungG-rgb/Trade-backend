package kg.tech.tradebackend.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardModel {
    Long id;

    String cardNumber;

    LocalDate expiryDate;

    @JsonProperty(value = "cvc_and_cvv")
    String cvcAndCvv;
}
