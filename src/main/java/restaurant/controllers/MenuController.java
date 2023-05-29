package restaurant.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Dish;
import restaurant.model.Menu;
import restaurant.model.Product;
import restaurant.model.dto.MenuDto;
import restaurant.model.dto.MenuPostDto;
import restaurant.model.mapper.DishMapper;
import restaurant.model.mapper.MenuMapper;
import restaurant.service.DishService;
import restaurant.service.MenuService;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/menus")
public class MenuController extends CrudController<Long, Menu, MenuDto, MenuPostDto> {

    private final MenuService menuService;
    private final DishService dishService;
    private final DishMapper dishMapper;

    public MenuController(MenuService menuService, DishService dishService) {
        super(Mappers.getMapper(MenuMapper.class), menuService);

        this.menuService = menuService;
        this.dishService = dishService;
        dishMapper = Mappers.getMapper(DishMapper.class);
    }

    @Override
    @Transactional
    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<MenuDto> updateTEntity(@PathVariable Long id, @RequestBody MenuPostDto menuPostDto) {
        try {
            Menu entity = mapper.postDtoToEntity(menuPostDto);

            List<Dish> dishList;
            if (menuPostDto.getDishesIds().size() != 0) {
                dishList = dishService.getByIds(menuPostDto.getDishesIds());
            } else {
                dishList = menuService.getById(id).getDishes();
            }
            entity.setDishes(dishList);

            Menu updatedTEntity = entityService.update(id, entity);
            return ok(mapper.entityToDto(updatedTEntity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("{menuId}/addDish/{dishId}/")
    public ResponseEntity<Object> addProductToDish(@PathVariable long dishId, @PathVariable long menuId) {
        try {
            Menu menu = menuService.getById(dishId);
            menu.getDishes().add(Dish.builder().id(dishId).build());

            Menu updatedMenu = menuService.update(menuId, menu);
            return ok(mapper.entityToDto(updatedMenu));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @Transactional
    @GetMapping("{menuId}/getDishes/")
    public ResponseEntity<?> getMenuDishes(@PathVariable long menuId) {
        try {
            Menu menu = menuService.getById(menuId);
            return ok(menu.getDishes().stream().map(dishMapper::entityToDto).collect(Collectors.toList()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

}

