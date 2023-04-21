package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Cart;
import kg.tech.tradebackend.domain.models.CartModel;
import org.mapstruct.Builder;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(builder = @Builder (disableBuilder = true), uses = OrderMapper.class)
public abstract class CartMapper {

    @Mapping(target = "orderModels", source = "orders")
    public abstract CartModel toModel(Cart cart);

    @InheritInverseConfiguration
    public abstract Cart toEntity(CartModel cartModel);

}
