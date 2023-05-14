package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.*;
import kg.tech.tradebackend.domain.models.CardModel;
import kg.tech.tradebackend.domain.models.RoleModel;
import kg.tech.tradebackend.domain.models.UserModel;
import kg.tech.tradebackend.domain.models.UserRegisterModel;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.util.ArrayList;
import java.util.List;

@Mapper(uses = {RoleMapper.class, CardMapper.class, PhoneMapper.class,
        CouponMapper.class, AddressMapper.class, OrderMapper.class}
)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "creditCard", source = "creditCardModel"),
            @Mapping(target = "address", source = "addressModel"),
            @Mapping(target = "phones", source = "phoneModels"),
            @Mapping(target = "coupons", source = "couponModels"),
            @Mapping(target = "roles", source = "roleModels")
    })
    User toEntity(UserModel userModel);

    default void update(User user, UserModel userModel) {
        user.setCreditCard( new Card(userModel.getCreditCardModel()) );
        user.setAddress( new Address(userModel.getAddressModel()));
        user.setId( userModel.getId() );
        user.setUsername( userModel.getUsername() );
        user.setPassword( userModel.getPassword() );
        user.setEmail( userModel.getEmail() );
        user.setBalance( userModel.getBalance() );
        user.setUpdatedAt( userModel.getUpdatedAt() );
        user.setCreatedAt( userModel.getCreatedAt() );

        if (userModel.getPhoneModels() != null && !userModel.getPhoneModels().isEmpty()) {
            List<Phone> phones = new ArrayList<>( userModel.getPhoneModels().size() );
            userModel.getPhoneModels()
                    .forEach(
                            phoneModel -> phones.add(new Phone(phoneModel.getId(), phoneModel.getNumber()))
                    );

            user.setPhones(phones);
        }
    }

    @InheritInverseConfiguration
    UserModel toModel(User user);

    @Mapping(target = "roles", source = "roleModels")
    User toEntity(UserRegisterModel userModel);

    @Mapping(target = "roleModels", source = "roles")
    UserRegisterModel toRegisterModel(User user);

    default void adminUpdate(User user, UserModel userModel) {
        user.setUsername(userModel.getUsername());
        user.setEmail(userModel.getEmail());
        user.setBalance(userModel.getBalance());
    }
}
