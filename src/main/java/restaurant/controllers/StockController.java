package restaurant.controllers;

import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Product;
import restaurant.model.Restaurant;
import restaurant.model.Stock;
import restaurant.model.dto.StockDto;
import restaurant.model.dto.StockPostDto;
import restaurant.model.dto.TableDto;
import restaurant.model.mapper.MapperBase;
import restaurant.model.mapper.RestaurantMapper;
import restaurant.model.mapper.StockMapper;
import restaurant.model.mapper.StockMapperImpl;
import restaurant.service.MenuService;
import restaurant.service.ProductService;
import restaurant.service.RestaurantService;
import restaurant.service.StockService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/stocks")
public class StockController {
    private final ProductService productService;
    private final StockService stockService;
    private final RestaurantService restaurantService;
    private final StockMapper mapper;

    public StockController(ProductService productService, StockService stockService, RestaurantService restaurantService) {
        this.mapper = Mappers.getMapper(StockMapper.class);
        this.productService = productService;
        this.stockService = stockService;
        this.restaurantService = restaurantService;
    }


    @GetMapping("{restaurantId}/get-stocks")
    public ResponseEntity<?> getStocks(@PathVariable Long restaurantId) {
        try {
            List<Stock> stockList = stockService.getStocksByRestaurantId(restaurantId);
            List<StockDto> stockDtos = stockList.stream()
                    .map(mapper::entityToDto)
                    .toList();
            return ok(stockDtos);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("{restaurantId}/synchronize-stock")
    @Transactional
    public ResponseEntity<?> synchronizeStocks(@PathVariable Long restaurantId) {
        try {
            Restaurant restaurant = restaurantService.getById(restaurantId);
            List<Product> productList = restaurant.getMenu().getDishes().stream()
                    .flatMap(dish -> dish.getDishProducts().stream().map(dishProduct -> dishProduct.getProduct()))
                    .distinct()
                    .collect(Collectors.toList());

            List<Stock> stockList = stockService.getStocksByRestaurantId(restaurantId);
            List<Product> existingProducts = stockList.stream()
                    .map(Stock::getProduct)
                    .collect(Collectors.toList());

            List<Product> newProducts = productList.stream()
                    .filter(product -> !existingProducts.contains(product))
                    .collect(Collectors.toList());

            for (Product product : newProducts) {
                Stock stock = Stock.builder()
                        .product(product)
                        .restaurant(restaurant)
                        .stock(0)
                        .isEnabled(false)
                        .build();
                stockService.create(stock);
            }
            return ok(new Object());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("{stockId}/addStock")
    @Transactional
    public ResponseEntity<?> addStock(@PathVariable Long stockId, @RequestParam Integer quantity) {
        try {
            Stock stock = stockService.getById(stockId);
            stock.setIsEnabled(true);
            stock.setStock(stock.getStock() + quantity);
            return ResponseEntity.status(HttpStatus.OK).body("Stock: " + stock.getStock());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("{stockId}/enableStock")
    @Transactional
    public ResponseEntity<?> enableStock(@PathVariable Long stockId, @RequestParam Boolean isEnable) {
        try {
            Stock stock = stockService.getById(stockId);
            stock.setIsEnabled(isEnable);
            return ResponseEntity.status(HttpStatus.OK).body("Stock: " + stock.getStock());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}