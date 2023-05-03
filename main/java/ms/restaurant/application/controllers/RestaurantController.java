package ms.restaurant.application.controllers;

import jakarta.validation.Valid;
import ms.restaurant.application.dto.menuDto.MenuDTO;
import ms.restaurant.application.dto.restaurantDto.RestaurantDTO;
import ms.restaurant.application.dto.restaurantTableDto.RestaurantTableDTO;
import ms.restaurant.application.dto.restaurantTableDto.TableNumberDTO;
import ms.restaurant.domain.facadeImpl.RestaurantFacadeImpl;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Menu;
import ms.restaurant.domain.model.RestaurantTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantFacadeImpl restaurantFacadeImpl;

    @Autowired
    public RestaurantController(RestaurantFacadeImpl restaurantFacadeImpl) {this.restaurantFacadeImpl = restaurantFacadeImpl;}

    @GetMapping("/{restaurantId}")
    public Optional<RestaurantDTO> getRestaurant(@PathVariable Long restaurantId) {
        return restaurantFacadeImpl.get(restaurantId);
    }

    @PostMapping("/add")
    public IDObject addRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO) {
        return restaurantFacadeImpl.add(restaurantDTO);
    }
    @PutMapping("/update/{restaurantId}")
    public void updateRestaurant(@Valid @RequestBody RestaurantDTO restaurantDTO, @PathVariable Long restaurantId) {
        restaurantFacadeImpl.update(restaurantDTO, restaurantId);
    }

    @DeleteMapping("/delete/{restaurantId}")
    public void deleteRestaurant(@PathVariable Long restaurantId) { restaurantFacadeImpl.delete(restaurantId); }


    @PostMapping("/add/menu/{restaurantId}")
    public void addMenuToRestaurant(@Valid @RequestBody MenuDTO menuDTO, @PathVariable Long restaurantId) {
        restaurantFacadeImpl.addMenuToRestaurant(menuDTO, restaurantId);
    }

    @DeleteMapping("/delete/menu/{restaurantId}")
    public void deleteMenuFromRestaurant(@RequestBody MenuDTO menuDTO, @PathVariable Long restaurantId) {
        restaurantFacadeImpl.deleteMenuFromRestaurant(menuDTO, restaurantId);
    }

    @PostMapping("/add/table/{restaurantId}")
    public void addTableToRestaurant(@Valid @RequestBody RestaurantTableDTO restaurantTableDTO, @PathVariable Long restaurantId) {
        restaurantFacadeImpl.addTableToRestaurant(restaurantTableDTO, restaurantId);
    }

    @DeleteMapping("/delete/table/{restaurantId}")
    public void deleteTableFromRestaurant(@RequestBody TableNumberDTO tableNumberDTO, @PathVariable Long restaurantId) {
        restaurantFacadeImpl.deleteTableFromRestaurant(tableNumberDTO, restaurantId);
    }

    @GetMapping("/allMenus/{restaurantId}")
    public List<Menu> getAllMenus(@PathVariable Long restaurantId) {
        return restaurantFacadeImpl.getAllMenus(restaurantId);
    }

    @GetMapping("/allTables/{restaurantId}")
    public List<RestaurantTable> getAllTables(@PathVariable Long restaurantId) {
        return restaurantFacadeImpl.getAllRestaurantTables(restaurantId);
    }
}
