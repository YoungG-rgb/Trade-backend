package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.models.CouponModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ApiCoupon")
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController extends BaseController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseModel<List<CouponModel>> getByUserId(@PathVariable Long userId) throws TradeException {
        return successResponse(userService.findCouponsByUserId(userId));
    }

    @PostMapping("/{userId}")
    public ResponseModel<CouponModel> apply(@PathVariable Long userId, @RequestParam(value = "couponUuid") String couponUuid) throws TradeException {
        return successResponse(userService.findByIdAndApplyCoupon(userId, couponUuid));
    }
}
