package restaurant.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.model.Table;

import java.util.List;


public interface TableRepository extends RepositoryBase<Table, Long> {
    List<Table> findTablesByRestaurantId(Long restaurantId);
}
