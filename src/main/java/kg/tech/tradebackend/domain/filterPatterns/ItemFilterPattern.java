package kg.tech.tradebackend.domain.filterPatterns;

import kg.tech.tradebackend.domain.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemFilterPattern {

    String name;
    BigDecimal priceTo;
    BigDecimal priceFrom;
    Double rating;
    Color dialColor;

}
