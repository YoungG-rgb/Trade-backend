package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.models.OrderModel;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController extends BaseController {
    private final OrderService orderService;

    @GetMapping("/{userId}")
    public ResponseModel<OrderModel> getUserOrders(@PathVariable("userId") Long userId){
        return successResponse(orderService.findByUserIdAndStatus(userId, OrderStatus.START));
    }

    @PostMapping
    public ResponseModel<OrderModel> create(@RequestParam(value = "applyCoupons", required = false) List<Long> applyCoupons,
                                            @RequestBody OrderModel orderModel) throws Exception {
        return successResponse(orderService.save(orderModel, applyCoupons));
    }

    @PutMapping
    public ResponseModel<OrderModel> update(@RequestBody OrderModel orderModel) throws OrderException {
        return successResponse(orderService.update(orderModel));
    }

    @DeleteMapping("/{id}")
    public ResponseModel<String> delete(@PathVariable("id") Long id) throws OrderException {
        orderService.delete(id);
        return successResponse("Удалено");
    }
}
