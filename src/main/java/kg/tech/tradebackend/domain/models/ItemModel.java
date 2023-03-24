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
    Double rating = 0d;
    String description;
    boolean active = true;
    List<ImageModel> imageModels;
}
