package kg.tech.tradebackend.utils;

import lombok.experimental.UtilityClass;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@UtilityClass
public class SpecificationHelper {

    public static String getContainsLikePattern(String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty() ? "%" : "%" + searchTerm.toLowerCase() + "%";
    }

    public static <T> Predicate getLikePredicateByField(String fieldName, String filterByField, CriteriaBuilder criteriaBuilder, Root<T> root) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(root.get(fieldName)), getContainsLikePattern(filterByField)
        );
    }

    public static boolean isEmpty(final CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static boolean isNotEmpty(final CharSequence charSequence) {
        return !isEmpty(charSequence);
    }
}