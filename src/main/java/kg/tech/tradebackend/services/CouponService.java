package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.filterPatterns.CouponFilterPattern;
import kg.tech.tradebackend.domain.models.CouponModel;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;

public interface CouponService {
    CouponModel save(BigDecimal bonus);
    CouponModel update(CouponModel couponModel) throws OrderException;

    void delete(Long couponId) throws OrderException;

    Page<CouponModel> getAll(CouponFilterPattern couponFilterPattern);

}
