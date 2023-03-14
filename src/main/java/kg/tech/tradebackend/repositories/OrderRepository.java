package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);
}
