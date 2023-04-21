package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponModel {
    Long id;

    String uuid;

    boolean isValid;

    BigDecimal bonus;
}
