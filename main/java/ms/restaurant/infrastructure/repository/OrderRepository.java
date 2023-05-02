package ms.restaurant.infrastructure.repository;

import ms.restaurant.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
