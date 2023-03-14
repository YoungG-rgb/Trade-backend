package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.models.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(uses = {RoleMapper.class}, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    User toEntity(UserModel userModel);

    UserModel toModel(User user);
}
