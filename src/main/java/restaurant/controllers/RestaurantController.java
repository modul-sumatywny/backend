package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.Restaurant;
import restaurant.model.dto.*;
import restaurant.model.mapper.DishMapper;
import restaurant.model.mapper.OrderMapper;
import restaurant.model.mapper.RestaurantMapper;
import restaurant.model.mapper.TableMapper;
import restaurant.service.RestaurantService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController extends CrudController<Long, Restaurant, RestaurantDto, RestaurantPostDto> {

    private final RestaurantService restaurantService;
    private final TableMapper tableMapper = Mappers.getMapper(TableMapper.class);
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    public RestaurantController(RestaurantService restaurantService) {
        super(Mappers.getMapper(RestaurantMapper.class), restaurantService);
        this.restaurantService = restaurantService;
    }

    @Override
    public ResponseEntity<List<RestaurantDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    public ResponseEntity<RestaurantDto> getTEntityById(@PathVariable Long id) {
        return super.getTEntityById(id);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<RestaurantDto> createTEntity(@RequestBody RestaurantPostDto entityPostDto) {
        return super.createTEntity(entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<RestaurantDto> updateTEntity(@PathVariable Long id, @RequestBody RestaurantPostDto entityPostDto) {
        return super.updateTEntity(id, entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<RestaurantDto> deleteTEntity(@PathVariable Long id) {
        return super.deleteTEntity(id);
    }

    @GetMapping("{restaurantId}/getTables")
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<?> getTables(@PathVariable Long restaurantId){
        try {
            Restaurant restaurant = restaurantService.getById(restaurantId);
            List<TableDto> tables = restaurant.getTables().stream()
                    .map(tableMapper::entityToDto)
                    .toList();

            return ok(tables);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("{restaurantId}/getOrders")
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<?> getOrders(@PathVariable Long restaurantId){
        try {
            Restaurant restaurant = restaurantService.getById(restaurantId);
            List<OrderDto> orders = restaurant.getOrders().stream()
                    .map(orderMapper::entityToDto)
                    .toList();

            return ok(orders);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
    @PutMapping("{restaurantId}/enableRestaurant")
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<?> enableRestaurant(@PathVariable Long restaurantId, @RequestParam boolean isEnabled){
        try {
            Restaurant restaurant = restaurantService.getById(restaurantId);
            restaurant.setIsEnabled(isEnabled);

            return ok(mapper.entityToDto(restaurantService.update(restaurantId,restaurant)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}
