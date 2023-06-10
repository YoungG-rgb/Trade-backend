package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Order;
import kg.tech.tradebackend.domain.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Order findByUserIdAndStatusIs(Long id, OrderStatus status);
}
