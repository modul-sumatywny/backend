package ms.restaurant.application.controllers;

import jakarta.validation.Valid;
import ms.restaurant.application.dto.restaurantTableDto.RestaurantTableDTO;
import ms.restaurant.domain.facadeImpl.RestaurantTableFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/table")
public class RestaurantTableController {
    private final RestaurantTableFacadeImpl restaurantTableFacadeImpl;

    @Autowired
    public RestaurantTableController(RestaurantTableFacadeImpl restaurantTableFacadeImpl) {this.restaurantTableFacadeImpl = restaurantTableFacadeImpl;}

    @GetMapping("/{restaurantTableId}")
    public Optional<RestaurantTableDTO> getRestaurantTable(@PathVariable Long restaurantTableId) {
        return restaurantTableFacadeImpl.get(restaurantTableId);
    }

    @DeleteMapping("/delete/{restaurantTableId}")
    public void deleteRestaurantTable(@PathVariable Long restaurantTableId) {
        restaurantTableFacadeImpl.delete(restaurantTableId);
    }

    @PutMapping("/update/{restaurantTableId}")
    public void updateRestaurantTable(@Valid @RequestBody RestaurantTableDTO restaurantTableDTO, @PathVariable Long restaurantTableId) {
        restaurantTableFacadeImpl.update(restaurantTableDTO, restaurantTableId);
    }


}
