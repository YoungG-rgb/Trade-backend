package kg.tech.tradebackend.domain.filterPatterns;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserFilterPattern {
    String usernameOrEmail;
    String town;
    String phoneNumber;
}
