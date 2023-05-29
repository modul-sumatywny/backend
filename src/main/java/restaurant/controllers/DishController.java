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
import restaurant.model.dto.DishDto;
import restaurant.model.dto.DishPostDto;
import restaurant.model.mapper.DishMapper;
import restaurant.model.mapper.ProductMapper;
import restaurant.service.DishService;
import restaurant.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/dishes")
public class DishController extends CrudController<Long, Dish, DishDto, DishPostDto> {

    private final DishService dishService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public DishController(DishService dishService, ProductService productService) {
        super(Mappers.getMapper(DishMapper.class), dishService);
        this.dishService = dishService;
        this.productService = productService;
        productMapper = Mappers.getMapper(ProductMapper.class);
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
    @PostMapping("{dishId}/product/{productId}")
    public ResponseEntity<Object> addProductToDish(@PathVariable long dishId, @PathVariable long productId) {
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

    @Transactional
    @GetMapping("{dishId}/products")
    public ResponseEntity<?> getDishProducts(@PathVariable long dishId) {
        try {
            Dish dish = dishService.getById(dishId);
            return ok(dish.getProducts().stream().map(productMapper::entityToDto).collect(Collectors.toList()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
