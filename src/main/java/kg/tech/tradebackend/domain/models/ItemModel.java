package kg.tech.tradebackend.domain.models;

import kg.tech.tradebackend.domain.enums.Color;
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
    Color dialColor;
    String glass;
    String waterResistance;
    String straps;
    int standardBatteryLife;
    Double rating;
    String description;
    boolean active;
    List<Long> imagesId;
}
