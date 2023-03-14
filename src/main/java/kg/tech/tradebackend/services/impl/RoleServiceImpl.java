package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.mappers.RoleMapper;
import kg.tech.tradebackend.domain.models.RoleModel;
import kg.tech.tradebackend.repositories.RoleRepository;
import kg.tech.tradebackend.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;

    @Override
    public RoleModel save(RoleModel roleModel) {
        roleRepository.save(roleMapper.toEntity(roleModel));
        return roleModel;
    }

    @Override
    public List<RoleModel> getRoles() {
        return roleRepository.findAll().stream().map(roleMapper::toModel).collect(Collectors.toList());
    }

}
