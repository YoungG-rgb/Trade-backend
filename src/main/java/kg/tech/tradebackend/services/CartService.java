package kg.tech.tradebackend.services;

import kg.tech.tradebackend.domain.exceptions.TradeException;

public interface CartService {
    boolean addToCartItem(Long itemId) throws TradeException;
}
