package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Cart;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.models.CartModel;
import kg.tech.tradebackend.repositories.UserRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(builder = @Builder (disableBuilder = true))
public abstract class CartMapper {
    @Autowired UserRepository userRepository;

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "simpleOrderViews", ignore = true)
    public abstract CartModel toModel(Cart cart);

    @Mapping(target = "user", expression = "java( userRepository.findById( cartModel.getUserId() ).get() )")
    public abstract Cart toEntity(CartModel cartModel);

    @BeforeMapping
    protected void mapOrderToSimpleOrderView(Cart cart, @MappingTarget CartModel cartModel) {
        User user = cart.getUser();

        if (user != null && user.getOrders() != null) cartModel.buildFromOrders(user.getOrders());
    }

}
