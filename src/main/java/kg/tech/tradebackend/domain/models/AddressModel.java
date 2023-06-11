package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    Double longitude;
    Double latitude;

    public List<Double> getCoordinates(){
        return List.of(latitude, longitude);
    }
}
