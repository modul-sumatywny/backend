package restaurant.application.controllers;

import restaurant.application.dto.productDto.ProductWithIdDTO;
import restaurant.application.dto.restaurantTableDto.RestaurantTableWithIdDTO;
import restaurant.domain.facadeImpl.ProductFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import restaurant.application.dto.productDto.ProductDTO;

import jakarta.validation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", allowedHeaders = "*")public class ProductController {
    private final ProductFacadeImpl productFacadeImpl;

    @Autowired
    public ProductController(ProductFacadeImpl productFacadeImpl) { this.productFacadeImpl = productFacadeImpl; }

    @GetMapping("/{productId}")
    public Optional<ProductDTO> getProduct(@PathVariable Long productId) {
        return productFacadeImpl.get(productId);
    }

    //nie można dodawać bezposrednio produktu bo kazdy produkt musi miec przypisane danie. Produkt mozna dodawac w kontrolerze Dish
//    @PostMapping("/add/{id}")
//    public IDObject addProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long id) {
//        return productFacadeImpl.addProductToDish(productDTO, id);
//    }

    @DeleteMapping("/delete/{productId}")
    public void deleteProduct(@PathVariable Long productId) { productFacadeImpl.delete(productId); }

    @PutMapping("/update/{productId}")
    public void updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long productId) {
        productFacadeImpl.update(productDTO, productId);
    }

    @GetMapping("/allProducts")
    public List<ProductWithIdDTO> getAllProducts() {
        return productFacadeImpl.getAll();
    }
}

