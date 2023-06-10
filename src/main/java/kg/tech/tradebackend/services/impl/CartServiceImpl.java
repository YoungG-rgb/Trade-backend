package kg.tech.tradebackend.services.impl;

import kg.tech.tradebackend.domain.entities.Item;
import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.entities.User;
import kg.tech.tradebackend.domain.enums.OrderStatus;
import kg.tech.tradebackend.domain.exceptions.TradeException;
import kg.tech.tradebackend.repositories.ItemRepository;
import kg.tech.tradebackend.repositories.OrderRepository;
import kg.tech.tradebackend.repositories.UserRepository;
import kg.tech.tradebackend.services.CartService;
import kg.tech.tradebackend.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    @Override
    public boolean addToCartItem(Long itemId) throws TradeException {
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new TradeException("ITEM NOT FOUND"));
        User user = userRepository.findByUsername(SecurityUtils.getAuthenticatedUsername())
                .orElseThrow(() -> new TradeException("USER IS NOT AUTHENTICATED"));

        Order order = orderRepository.findByUserIdAndStatusIs(user.getId(), OrderStatus.START);

        if (order == null) {
            initializeOrder(item, user);
        } else {
            order.getItems().add(item);
            order.addToTotal(item.getPrice());
            orderRepository.save(order);
        }

        log.info(String.format("item %s added to cart", item.getName()));
        return true;
    }

    private void initializeOrder(Item item, User user) {
        List<Item> items = new ArrayList<>(1);
        Order order = new Order();

        items.add(item);
        order.setUser(user);
        order.setItems(items);
        order.setTotal(item.getPrice());
        order.setStatus(OrderStatus.START);
        order.setCreatedAt(LocalDateTime.now());
        orderRepository.save(order);
    }
}
