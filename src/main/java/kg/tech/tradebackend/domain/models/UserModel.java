package kg.tech.tradebackend.domain.models;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserModel {
    Long id;

    @NonNull
    String username;

    @NonNull
    String password;

    @NonNull
    String email;

    BigDecimal balance;
    LocalDate updatedAt;
    List<OrderModel> orders;
    List<RoleModel> roles;
}
