package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
