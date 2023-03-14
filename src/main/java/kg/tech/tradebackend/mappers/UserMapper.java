package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.models.UserModel;
import org.mapstruct.*;

@Mapper(uses = {RoleMapper.class, CardMapper.class, PhoneMapper.class,
        CouponMapper.class, AddressMapper.class, OrderMapper.class, CartMapper.class}
)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "cart", source = "cartModel"),
            @Mapping(target = "creditCard", source = "creditCardModel"),
            @Mapping(target = "address", source = "addressModel"),
            @Mapping(target = "phones", source = "phoneModels"),
            @Mapping(target = "coupons", source = "couponModels"),
            @Mapping(target = "orders", source = "orderModels"),
            @Mapping(target = "roles", source = "roleModels")
    })
    User toEntity(UserModel userModel);

    @InheritInverseConfiguration
    UserModel toModel(User user);
}