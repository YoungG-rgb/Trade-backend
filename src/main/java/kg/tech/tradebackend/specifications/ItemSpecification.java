package kg.tech.tradebackend.specifications;

import kg.tech.tradebackend.utils.SpecificationHelper;
import kg.tech.tradebackend.domain.entities.Item;
import kg.tech.tradebackend.domain.filterPatterns.ItemFilterPattern;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class ItemSpecification implements Specification<Item> {
    private final ItemFilterPattern filterPattern;

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (filterPattern.getName() != null) {
            predicates.add(SpecificationHelper.getLikePredicateByField("name", filterPattern.getName(), criteriaBuilder, root));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
