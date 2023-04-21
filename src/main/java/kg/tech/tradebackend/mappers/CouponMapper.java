package kg.tech.tradebackend.mappers;


import kg.tech.tradebackend.domain.entities.Coupon;
import kg.tech.tradebackend.domain.models.CouponModel;
import org.mapstruct.Mapper;

@Mapper
public interface CouponMapper {

    CouponModel toModel(Coupon coupon);

    Coupon toEntity(CouponModel couponModel);
}
