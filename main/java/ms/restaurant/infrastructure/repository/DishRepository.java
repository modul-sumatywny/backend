package ms.restaurant.infrastructure.repository;

import ms.restaurant.domain.model.Dish;
import ms.restaurant.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DishRepository extends JpaRepository<Dish, Long> {
    boolean existsByName(String name);
    Optional<Dish> findByName(String name);
}
