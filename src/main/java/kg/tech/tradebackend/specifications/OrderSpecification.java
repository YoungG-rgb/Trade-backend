package kg.tech.tradebackend.specifications;

import kg.tech.tradebackend.domain.entities.Address;
import kg.tech.tradebackend.domain.entities.Item;
import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.filterPatterns.OrderFilterPattern;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OrderSpecification implements Specification<Order> {
    private final OrderFilterPattern orderFilterPattern;
    private final User user;

    @Override
    public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>(2);
        Join<User, Order> orderUserJoin = root.join("user");
        predicates.add( criteriaBuilder.like( orderUserJoin.get("username"), user.getUsername() ) );
        predicates.add( criteriaBuilder.equal(root.get("status"), orderFilterPattern.getOrderStatus()) );

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
