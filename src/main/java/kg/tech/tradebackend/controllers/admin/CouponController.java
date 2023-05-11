package kg.tech.tradebackend.controllers.admin;

import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.filterPatterns.CouponFilterPattern;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.domain.models.data_tables.DataTablePage;
import kg.tech.tradebackend.services.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/coupons")
public class CouponController {
    private final CouponService couponService;

    @PostMapping("/filter")
    public ResponseEntity<DataTablePage<CouponModel>> getAll(@RequestBody CouponFilterPattern couponFilterPattern){
        return ResponseEntity.ok(new DataTablePage<>(couponFilterPattern, couponService.getAll(couponFilterPattern)));
    }

    @PostMapping("/{bonus}")
    public ResponseEntity<CouponModel> save(@PathVariable BigDecimal bonus) {
        return ResponseEntity.ok(couponService.save(bonus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) throws OrderException {
        couponService.delete(id);
        return ResponseEntity.ok("Удалено");
    }

    @PutMapping
    public ResponseEntity<CouponModel> update(@RequestBody CouponModel couponModel) throws OrderException {
        return ResponseEntity.ok(couponService.update(couponModel));
    }
}
