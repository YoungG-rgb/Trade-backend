package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.filterPatterns.RoleFilterPattern;
import kg.tech.tradebackend.domain.models.RoleModel;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RoleService {
    RoleModel save(RoleModel roleModel);

    List<RoleModel> getRoles();

    RoleModel findById(Long id) throws Exception;
    Page<RoleModel> filter(RoleFilterPattern roleFilterPattern);
    RoleModel update(RoleModel roleModel) throws Exception;
    void delete(Long id) throws Exception;
}
