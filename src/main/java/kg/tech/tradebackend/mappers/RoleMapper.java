package kg.tech.tradebackend.mappers;

import kg.tech.tradebackend.domain.entities.Role;
import kg.tech.tradebackend.domain.models.RoleModel;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {

    Role toEntity(RoleModel roleModel);

    RoleModel toModel(Role role);
}
