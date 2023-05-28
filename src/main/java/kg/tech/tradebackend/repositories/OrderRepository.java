package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByUserIdAndStatusIs(Long id, OrderStatus status);
}
