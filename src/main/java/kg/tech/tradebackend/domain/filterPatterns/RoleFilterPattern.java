package kg.tech.tradebackend.domain.filterPatterns;

import kg.tech.tradebackend.domain.models.data_tables.DatatablePaginationCriteria;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleFilterPattern extends DatatablePaginationCriteria {
    String name;
}
