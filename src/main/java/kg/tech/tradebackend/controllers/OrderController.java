package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.exceptions.OrderException;
import kg.tech.tradebackend.domain.filterPatterns.OrderFilterPattern;
import kg.tech.tradebackend.domain.models.ItemModel;
import kg.tech.tradebackend.domain.models.OrderModel;
import kg.tech.tradebackend.domain.models.data_tables.DataTablePage;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.OrderService;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    public ResponseModel<OrderModel> create(@RequestBody OrderModel orderModel) throws Exception {
        return successResponse(orderService.save(orderModel));
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

    @PostMapping("/my-orders")
    public DataTablePage<OrderModel> getMyOrders(@RequestBody OrderFilterPattern orderFilterPattern) throws OrderException {
        return new DataTablePage<>(orderFilterPattern, orderService.findByUsername(SecurityUtils.getAuthenticatedUsername(), orderFilterPattern));
    }
}
