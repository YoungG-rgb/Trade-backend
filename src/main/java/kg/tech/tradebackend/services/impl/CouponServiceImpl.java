package kg.tech.tradebackend.services.impl;


import kg.tech.tradebackend.domain.entities.Coupon;
import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.filterPatterns.CouponFilterPattern;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.mappers.CouponMapper;
import kg.tech.tradebackend.repositories.CouponRepository;
import kg.tech.tradebackend.services.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static kg.tech.tradebackend.utils.SpecificationHelper.getLikePredicateByField;
import static kg.tech.tradebackend.utils.SpecificationHelper.isNotEmpty;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponModel save(BigDecimal bonus) {
        CouponModel couponModel = CouponModel.builder()
                .uuid(UUID.randomUUID().toString())
                .bonus(bonus)
                .isValid(true)
                .build();

        couponRepository.save(couponMapper.toEntity(couponModel));
        return couponModel;
    }

    @Override
    public CouponModel update(CouponModel couponModel) throws OrderException {
        if (couponModel.getId() == null) throw new OrderException("Купон не валиден");

        couponRepository.save(couponMapper.toEntity(couponModel));
        return couponModel;
    }

    @Override
    public void delete(Long couponId) throws OrderException {
        couponRepository.delete(
                couponRepository
                        .findById(couponId)
                        .orElseThrow(() -> new OrderException("COUPON_NOT_FOUND"))
        );
    }

    @Override
    public Page<CouponModel> getAll(CouponFilterPattern couponFilterPattern) {
        Page<Coupon> couponPage = couponRepository.findAll((Specification<Coupon>) (root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (isNotEmpty(couponFilterPattern.getUuid())) {
                predicates.add( getLikePredicateByField("uuid", couponFilterPattern.getUuid(), criteriaBuilder, root) );
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        }, couponFilterPattern.toPageRequest());

        return new PageImpl<>(
                couponPage.stream().map(couponMapper::toModel).toList(),
                couponFilterPattern.toPageRequest(),
                couponPage.getTotalPages()
        );
    }

}
