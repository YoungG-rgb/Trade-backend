package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressModel {
    Long id;
    String town;
    String street;
    String houseNumber;
    String houseType;
}
