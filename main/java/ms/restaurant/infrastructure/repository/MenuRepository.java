package ms.restaurant.infrastructure.repository;

import ms.restaurant.domain.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    boolean existsByName(String name);
}
