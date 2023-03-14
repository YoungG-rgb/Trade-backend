package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imagesId")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemModel {
    Long id;
    String name;
    BigDecimal price;
    Integer count;
    Double rating;
    String description;
    boolean active;
    List<Long> imagesId;
}
