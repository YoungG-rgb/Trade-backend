package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

}
