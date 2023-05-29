package restaurant.service;

import restaurant.model.Dish;
import restaurant.repository.DishRepository;
import org.springframework.stereotype.Service;

@Service
public class DishService extends CrudService<Long, Dish> {

    private final DishRepository dishRepository;

    public DishService(DishRepository dishRepository) {
        super(dishRepository, Dish.class);

        this.dishRepository = dishRepository;
    }
}
