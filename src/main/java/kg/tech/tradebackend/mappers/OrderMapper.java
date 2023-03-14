package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.models.OrderModel;
import kg.tech.tradebackend.repositories.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = ItemMapper.class)
public abstract class OrderMapper {
    @Autowired protected UserRepository userRepository;

    @Mapping(target = "userId", source = "user.id")
    public abstract OrderModel toModel(Order order);

    @Mapping(target = "user", expression = "java( userRepository.findById(orderModel.getUserId()).get() )")
    public abstract Order toEntity(OrderModel orderModel);
}
