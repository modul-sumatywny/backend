package restaurant.service;

import restaurant.model.Restaurant;
import restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService extends CrudService<Long, Restaurant> {
    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        super(restaurantRepository, Restaurant.class);

        this.restaurantRepository = restaurantRepository;
    }
}
