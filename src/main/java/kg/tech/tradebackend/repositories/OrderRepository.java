package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Order findByUserIdAndStatusIs(Long id, OrderStatus status);

    List<Order> findByUserId(Long userId);
}
