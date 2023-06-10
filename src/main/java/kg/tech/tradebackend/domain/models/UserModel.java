package kg.tech.tradebackend.domain.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
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
    CardModel creditCardModel;

    List<PhoneModel> phoneModels;
    List<CouponModel> couponModels;
    List<RoleModel> roleModels;

    public String getAddressInfo(){
        if (addressModel != null) {
            return addressModel.getTown() +
                    " " +
                    addressModel.getStreet() +
                    " " +
                    addressModel.getHouseNumber() +
                    "кв";
        }
        return null;
    }
}
