package restaurant.repository;

import org.springframework.stereotype.Repository;
import restaurant.model.Dish;

@Repository
public interface DishRepository extends RepositoryBase<Dish, Long> {
}
