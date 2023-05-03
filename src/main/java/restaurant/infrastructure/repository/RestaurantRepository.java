package restaurant.infrastructure.repository;

import restaurant.domain.model.Product;
import restaurant.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    boolean existsByName(String name);
    boolean existsByAddress(String address);
    Optional<Restaurant> findByName(String name);
    Optional<Restaurant> findByAddress(String address);
}
