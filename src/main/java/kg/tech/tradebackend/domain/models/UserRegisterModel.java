package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterModel {
    Long id;

    @NonNull
    String username;

    @NonNull
    String password;

    @NonNull
    String email;

    List<RoleModel> roleModels;
}
