package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageModel {
    Long id;

    byte [] picture;

    boolean isMain = false;

}
