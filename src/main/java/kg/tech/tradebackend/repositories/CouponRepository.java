package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c where c.id in (:ids) and c.isValid = true")
    List<Coupon> findAllByIdInAndValidIsTrue(List<Long> ids);
}
