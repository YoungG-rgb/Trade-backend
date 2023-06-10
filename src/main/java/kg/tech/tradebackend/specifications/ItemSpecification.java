package kg.tech.tradebackend.specifications;

import kg.tech.tradebackend.domain.enums.Color;
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

import static kg.tech.tradebackend.utils.SpecificationHelper.isEmpty;

@AllArgsConstructor
public class ItemSpecification implements Specification<Item> {
    private final ItemFilterPattern filterPattern;

    @Override
    public Predicate toPredicate(Root<Item> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (!isEmpty(filterPattern.getName())) {
            predicates.add(SpecificationHelper.getLikePredicateByField("name", filterPattern.getName(), criteriaBuilder, root));
        }

        if (!isEmpty(filterPattern.getDialColor())) {
            predicates.add(criteriaBuilder.equal(root.get("dialColor"), Color.valueOf(filterPattern.getDialColor())));
        }

        if (filterPattern.getPriceFrom() != null) {
            predicates.add(criteriaBuilder.gt(root.get("price"), filterPattern.getPriceFrom()));
        }

        if (filterPattern.getPriceFrom() != null && filterPattern.getPriceTo() != null) {
            predicates.add(
                    criteriaBuilder.between(
                            root.get("price"),
                            filterPattern.getPriceFrom(),
                            filterPattern.getPriceTo()
                    )
            );
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

}
