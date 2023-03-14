package kg.tech.tradebackend.mappers;


import kg.tech.tradebackend.domain.entities.Coupon;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.repositories.UserRepository;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class CouponMapper {
    @Autowired protected UserRepository userRepository;

    @InheritInverseConfiguration
    public abstract CouponModel toModel(Coupon coupon);

    @Mapping(target = "user", expression = "java( userRepository.findById(couponModel.getUserId()).get() )")
    public abstract Coupon toEntity(CouponModel couponModel);
}
