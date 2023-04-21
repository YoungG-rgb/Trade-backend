package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController extends BaseController {
    private final CouponService couponService;

    @GetMapping
    public ResponseModel<List<CouponModel>> getAll(){
        return successResponse(couponService.getAll());
    }

    @PostMapping
    public ResponseModel<CouponModel> save(@RequestBody CouponModel couponModel) {
        return successResponse(couponService.save(couponModel));
    }

    @DeleteMapping("/{id}")
    public ResponseModel<String> delete(@PathVariable Long id) throws OrderException {
        couponService.delete(id);
        return successResponse("Удалено");
    }
}
