package ms.restaurant.infrastructure.repository;


import ms.restaurant.domain.model.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}
