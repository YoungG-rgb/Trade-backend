package kg.tech.tradebackend.specifications;

import kg.tech.tradebackend.domain.entities.Address;
import kg.tech.tradebackend.domain.entities.Phone;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.filterPatterns.UserFilterPattern;
import kg.tech.tradebackend.utils.SpecificationHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

    private final UserFilterPattern userFilterPattern;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (userFilterPattern.getUsernameOrEmail() != null) {
            predicates.add(
                    criteriaBuilder.like( criteriaBuilder.lower( criteriaBuilder.concat( root.get("username"), root.get("email") ) ),
                            SpecificationHelper.getContainsLikePattern(userFilterPattern.getUsernameOrEmail())
                    )
            );
        }

        if (userFilterPattern.getPhoneNumber() != null) {
            Join<Phone, User> phoneUserJoin = root.join("phones");
            predicates.add( criteriaBuilder.like( phoneUserJoin.get("number"), userFilterPattern.getPhoneNumber()) );
        }

        if (userFilterPattern.getTown() != null) {
            Join<Address, User> addressUserJoin = root.join("address");
            predicates.add( criteriaBuilder.like( addressUserJoin.get("town"), userFilterPattern.getTown() ) );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
