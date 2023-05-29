package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Coupon;
import kg.tech.tradebackend.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, JpaSpecificationExecutor<Coupon> {

    @Query("select c from Coupon c where c.id in (:ids) and c.isValid = true")
    List<Coupon> findAllByIdInAndValidIsTrue(List<Long> ids);

    Optional<Coupon> findByUuid(String uuid);
}
