package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.models.CouponModel;

import java.util.List;

public interface CouponService {
    CouponModel save(CouponModel couponModel);
    CouponModel update(CouponModel couponModel) throws OrderException;

    void delete(Long couponId) throws OrderException;

    List<CouponModel> getAll();

    List<CouponModel> findByUserId(Long userId);

}
