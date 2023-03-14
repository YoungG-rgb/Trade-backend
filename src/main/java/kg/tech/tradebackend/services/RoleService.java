package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.models.RoleModel;

import java.util.List;

public interface RoleService {
    RoleModel save(RoleModel roleModel);

    List<RoleModel> getRoles();
}
