package kg.tech.tradebackend.services.impl;


import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.mappers.CouponMapper;
import kg.tech.tradebackend.repositories.CouponRepository;
import kg.tech.tradebackend.services.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    private final CouponRepository couponRepository;
    private final CouponMapper couponMapper;

    @Override
    public CouponModel save(CouponModel couponModel) {
        couponRepository.save(couponMapper.toEntity(couponModel));
        return couponModel;
    }

    @Override
    public CouponModel update(CouponModel couponModel) throws OrderException {
        if (couponModel.getId() == null) throw new OrderException("Купон не валиден");
        return this.save(couponModel);
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
    public List<CouponModel> getAll() {
        return couponRepository
                .findAll()
                .stream()
                .map(couponMapper::toModel)
                .toList();
    }

    @Override
    public List<CouponModel> findByUserId(Long userId) {
        return couponRepository
                .findByUserId(userId)
                .stream()
                .map(couponMapper::toModel)
                .toList();
    }
}
