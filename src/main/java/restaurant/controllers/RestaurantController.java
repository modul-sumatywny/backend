package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Restaurant;
import restaurant.model.dto.RestaurantDto;
import restaurant.model.dto.RestaurantPostDto;
import restaurant.model.mapper.RestaurantMapper;
import restaurant.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController extends CrudController<Long, Restaurant, RestaurantDto, RestaurantPostDto> {

    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        super(Mappers.getMapper(RestaurantMapper.class), restaurantService);
        this.restaurantService = restaurantService;
    }
}
