package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
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
    LocalDate createdAt;

    AddressModel addressModel;
    CartModel cartModel;
    CardModel creditCardModel;

    List<PhoneModel> phoneModels;
    List<CouponModel> couponModels;
    List<OrderModel> orderModels;
    List<RoleModel> roleModels;
}
