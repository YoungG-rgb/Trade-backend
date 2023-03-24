package kg.tech.tradebackend.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImageModel {
    Long id;

    String name;

    @JsonProperty(required = true)
    String base64;

    @JsonProperty(required = true)
    String format;

    boolean isMain = false;

}
