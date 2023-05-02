package ms.restaurant.application.controllers;

import jakarta.validation.Valid;
import ms.restaurant.application.dto.dishDto.DishDTO;
import ms.restaurant.application.dto.menuDto.MenuDTO;
import ms.restaurant.application.dto.menuDto.RestaurantDTO;
import ms.restaurant.domain.facadeImpl.RestaurantFacadeImpl;
import ms.restaurant.domain.model.IDObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantFacadeImpl restaurantFacadeImpl;

    @Autowired
    public RestaurantController(RestaurantFacadeImpl restaurantFacadeImpl) {this.restaurantFacadeImpl = restaurantFacadeImpl;}

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getRestaurant(@PathVariable Long id) {
        return restaurantFacadeImpl.getRestaurant(id);
    }

    @PostMapping("/add")
    public IDObject addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        return restaurantFacadeImpl.add(restaurantDTO);
    }
    @PutMapping("/update/{id}")
    public void updateRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO, @PathVariable Long id) {
        restaurantFacadeImpl.update(restaurantDTO, id);
    }

    @PostMapping("/add/menu/{restaurantId}")
    public void addMenuToRestaurant(@Valid @RequestBody MenuDTO menuDTO, @PathVariable Long restaurantId) {
        restaurantFacadeImpl.addMenuToRestaurant(menuDTO, restaurantId);
    }

    @DeleteMapping("/delete/menu/{restaurantId}")
    public void deleteMenuFromRestaurant(@RequestBody MenuDTO menuDTO, @PathVariable Long restaurantId) {
        restaurantFacadeImpl.deleteMenuFromRestaurant(menuDTO, restaurantId);
    }
}
