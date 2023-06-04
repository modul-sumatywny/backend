package restaurant.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Dish;
import restaurant.model.Product;
import restaurant.model.dto.*;
import restaurant.model.mapper.DishMapper;
import restaurant.model.mapper.MenuMapper;
import restaurant.model.mapper.OrderMapper;
import restaurant.model.mapper.ProductMapper;
import restaurant.service.DishService;
import restaurant.service.ProductService;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/dishes")
public class DishController extends CrudController<Long, Dish, DishDto, DishPostDto> {

    private final DishService dishService;
    private final ProductService productService;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final MenuMapper menuMapper = Mappers.getMapper(MenuMapper.class);

    public DishController(DishService dishService, ProductService productService) {
        super(Mappers.getMapper(DishMapper.class), dishService);
        this.dishService = dishService;
        this.productService = productService;
    }

    @Override
    @Transactional
    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DishDto> updateTEntity(@PathVariable Long id, @RequestBody DishPostDto dishPostDto) {
        try {
            Dish entity = mapper.postDtoToEntity(dishPostDto);

            List<Product> productList;
            if (dishPostDto.getProductsIds().size() > 0) {
                productList = productService.getByIds(dishPostDto.getProductsIds());
            } else {
                productList = entityService.getById(id).getProducts();
            }
            entity.setProducts(productList);

            Dish updatedTEntity = entityService.update(id, entity);
            return ok(mapper.entityToDto(updatedTEntity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("{dishId}/addProduct/{productId}")
    public ResponseEntity<Object> addProduct(@PathVariable Long dishId, @PathVariable Long productId) {
        try {
            Dish dish = dishService.getById(dishId);
            Product product = productService.getById(productId);
            dish.getProducts().add(product);

            Dish updatedDish = dishService.update(dishId, dish);
            return ok(mapper.entityToDto(updatedDish));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @GetMapping("{dishId}/getMenus")
    public ResponseEntity<?> getMenus(@PathVariable Long dishId) {
        try {
            Dish dish = dishService.getById(dishId);
            List<MenuDto> menus = dish.getMenus().stream()
                    .map(menuMapper::entityToDto)
                    .toList();

            return ok(menus);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping("{dishId}/getProducts")
    public ResponseEntity<?> getProducts(@PathVariable Long dishId) {
        try {
            Dish dish = dishService.getById(dishId);
            List<ProductDto> products = dish.getProducts().stream()
                    .map(productMapper::entityToDto)
                    .toList();

            return ok(products);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
