package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Role;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.filterPatterns.RoleFilterPattern;
import kg.tech.tradebackend.mappers.RoleMapper;
import kg.tech.tradebackend.domain.models.RoleModel;
import kg.tech.tradebackend.repositories.RoleRepository;
import kg.tech.tradebackend.services.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static kg.tech.tradebackend.utils.SpecificationHelper.getLikePredicateByField;
import static kg.tech.tradebackend.utils.SpecificationHelper.isNotEmpty;

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
        return roleRepository.findAll().stream().map(roleMapper::toModel).toList();
    }

    @Override
    public RoleModel findById(Long id) throws Exception {
        Role role = roleRepository.findById(id).orElseThrow(() -> new TradeException("ROLE_NOT_FOUND"));
        return roleMapper.toModel(role);
    }

    @Override
    public Page<RoleModel> filter(RoleFilterPattern roleFilterPattern) {
        Page<Role> rolePage = roleRepository.findAll((Specification<Role>) (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isNotEmpty(roleFilterPattern.getName())) {
                predicates.add( getLikePredicateByField("name", roleFilterPattern.getName(), criteriaBuilder, root) );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, roleFilterPattern.toPageRequest());


        return new PageImpl<>(
                rolePage.stream().map(roleMapper::toModel).toList(),
                roleFilterPattern.toPageRequest(),
                rolePage.getTotalPages()
        );
    }

    @Override
    public RoleModel update(RoleModel roleModel) throws Exception {
        return roleMapper.toModel(
                roleRepository.save(roleMapper.toEntity(roleModel))
        );
    }

    @Override
    public void delete(Long id) throws Exception {
        roleRepository.delete(
                roleRepository.findById(id).orElseThrow(() -> new TradeException("ROLE_NOT_FOUND"))
        );
    }
}
