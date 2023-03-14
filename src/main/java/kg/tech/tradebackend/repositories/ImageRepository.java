package kg.tech.tradebackend.repositories;

import kg.tech.tradebackend.domain.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
