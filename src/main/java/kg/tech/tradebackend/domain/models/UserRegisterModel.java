package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterModel {
    Long id;

    @NonNull
    String username;

    @NonNull
    String password;

    @NonNull
    String email;

}
