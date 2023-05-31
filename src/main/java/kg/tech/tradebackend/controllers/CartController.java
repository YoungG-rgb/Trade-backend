package kg.tech.tradebackend.controllers;

import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.domain.response.ResponseModel;
import kg.tech.tradebackend.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController extends BaseController {
    private final CartService cartService;

    @PostMapping("/{itemId}")
    public ResponseModel<Boolean> addToCart(@PathVariable Long itemId) throws TradeException {
        return successResponse(cartService.addToCartItem(itemId));
    }
}
