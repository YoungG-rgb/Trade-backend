package kg.tech.tradebackend.domain.filterPatterns;

import kg.tech.tradebackend.domain.enums.Color;
import kg.tech.tradebackend.domain.models.data_tables.DatatablePaginationCriteria;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ItemFilterPattern extends DatatablePaginationCriteria {

    String name;
    BigDecimal priceTo;
    BigDecimal priceFrom;
    Double rating;
    Color dialColor;

}
