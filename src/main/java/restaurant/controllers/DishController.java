package restaurant.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Dish;
import restaurant.model.DishProduct;
import restaurant.model.DishProductId;
import restaurant.model.Product;
import restaurant.model.dto.*;
import restaurant.model.mapper.DishMapper;
import restaurant.model.mapper.MenuMapper;
import restaurant.model.mapper.OrderMapper;
import restaurant.model.mapper.ProductMapper;
import restaurant.service.DishProductService;
import restaurant.service.DishService;
import restaurant.service.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/dishes")
public class DishController extends CrudController<Long, Dish, DishDto, DishPostDto> {

    private final DishService dishService;
    private final ProductService productService;
    private final DishProductService dishProductService;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);
    private final MenuMapper menuMapper = Mappers.getMapper(MenuMapper.class);

    public DishController(DishService dishService, ProductService productService, DishProductService dishProductService) {
        super(Mappers.getMapper(DishMapper.class), dishService);
        this.dishService = dishService;
        this.productService = productService;
        this.dishProductService = dishProductService;
    }

    @Override
    public ResponseEntity<List<DishDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    public ResponseEntity<DishDto> getTEntityById(Long aLong) {
        return super.getTEntityById(aLong);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<DishDto> deleteTEntity(Long aLong) {
        return super.deleteTEntity(aLong);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DishDto> createTEntity(@RequestBody DishPostDto entityPostDto) {
        try {
            Dish entity = mapper.postDtoToEntity(entityPostDto);
            Dish createdTEntity = entityService.create(entity);
            List<DishProduct> productList = getDishProducts(entity, createdTEntity);

            for (DishProduct dishProduct : productList) {
                dishProductService.create(dishProduct);
            }

            return ok(mapper.entityToDto(createdTEntity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DishDto> updateTEntity(@PathVariable Long id, @RequestBody DishPostDto dishPostDto) {
        try {
            Dish entity = mapper.postDtoToEntity(dishPostDto);
            entity.setId(id);
            List<DishProduct> productList = getDishProducts(entity, entity);
            entity.setDishProducts(productList);
            Dish updatedEntity = entityService.update(id, entity);
            List<DishProduct> existingDishProducts = dishProductService.getByDishId(id);
            List<DishProduct> productsToRemove = existingDishProducts.stream()
                    .filter(existingProduct -> productList.stream()
                            .noneMatch(newProduct -> existingProduct.getId().equals(newProduct.getId())))
                    .collect(Collectors.toList());

            productsToRemove.forEach(productToRemove ->
                    dishProductService.delete(productToRemove.getProduct().getId(), productToRemove.getId().getDish()));

            return ok(mapper.entityToDto(updatedEntity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    @PostMapping("{dishId}/addProduct/{productId}")
    public ResponseEntity<Object> addProduct(@PathVariable Long dishId, @PathVariable Long productId,@RequestParam Integer quantity) {
        try {
            Dish dish = dishService.getById(dishId);
            Product product = productService.getById(productId);

            if (!dish.getDishProducts().contains(product)) {
                DishProduct dishProduct = DishProduct.builder()
                        .id(DishProductId.builder().product(productId).dish(dishId).build())
                        .dish(dish)
                        .product(product)
                        .quantity(quantity)  // Ustaw ilość produktu na 1 lub odpowiednią wartość
                        .build();
                dish.getDishProducts().add(dishProduct);
            }

            Dish updatedDish = dishService.update(dishId, dish);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
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

    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    @GetMapping("{dishId}/getProducts")
    public ResponseEntity<List<ProductDto>> getProducts(@PathVariable Long dishId) {
        try {
            Dish dish = dishService.getById(dishId);
            List<ProductDto> products = dish.getDishProducts().stream()
                    .map(DishProduct::getProduct)
                    .map(productMapper::entityToDto)
                    .toList();

            return ResponseEntity.ok(products);
        } catch (Exception ex) {
            throw new EntityNotFoundException(ex.getMessage());
        }
    }

    private static List<DishProduct> getDishProducts(Dish entity, Dish createdTEntity) {
        List<DishProduct> productList = entity.getDishProducts().stream()
                .map(dishProduct -> DishProduct.builder()
                        .id(DishProductId.builder()
                                .product(dishProduct.getId().getProduct())
                                .dish(createdTEntity.getId())
                                .build())
                        .dish(Dish.builder().id(createdTEntity.getId()).build())
                        .product(Product.builder().id(dishProduct.getId().getProduct()).build())
                        .quantity(dishProduct.getQuantity())
                        .build())
                .toList();
        return productList;
    }
}
