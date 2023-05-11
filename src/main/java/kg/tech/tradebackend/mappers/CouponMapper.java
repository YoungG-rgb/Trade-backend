package kg.tech.tradebackend.mappers;


import kg.tech.tradebackend.domain.entities.Coupon;
import kg.tech.tradebackend.domain.models.CouponModel;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CouponMapper {

    @Mapping(target = "isValid", source = "valid")
    CouponModel toModel(Coupon coupon);

    @Mapping(target = "valid", source = "valid")
    Coupon toEntity(CouponModel couponModel);
}
