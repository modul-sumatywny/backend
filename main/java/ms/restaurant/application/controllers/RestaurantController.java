package ms.restaurant.application.controllers;

import jakarta.validation.Valid;
import ms.restaurant.application.dto.RestaurantDTO;
import ms.restaurant.domain.facadeImpl.RestaurantFacadeImpl;
import ms.restaurant.domain.model.IDObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantFacadeImpl restaurantFacadeImpl;

    @Autowired
    public RestaurantController(RestaurantFacadeImpl restaurantFacadeImpl) {
        this.restaurantFacadeImpl = restaurantFacadeImpl;
    }

    @GetMapping("/{id}")
    public Optional<RestaurantDTO> get(Long id) {
        return  restaurantFacadeImpl.get(id);
    }

    @PostMapping("/add")
    public IDObject addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        return restaurantFacadeImpl.add(restaurantDTO);
    }
}
