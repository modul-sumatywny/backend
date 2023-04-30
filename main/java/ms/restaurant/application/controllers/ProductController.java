package ms.restaurant.application.controllers;

import ms.restaurant.domain.facadeImpl.ProductFacadeImpl;
import ms.restaurant.domain.model.IDObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ms.restaurant.application.dto.ProductDTO;

import jakarta.validation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductFacadeImpl productFacadeImpl;

    @Autowired
    public ProductController(ProductFacadeImpl productFacadeImpl) { this.productFacadeImpl = productFacadeImpl; }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getProduct(@PathVariable Long id) {
        return productFacadeImpl.getProduct(id);
    }

    @PostMapping("/add")
    public IDObject addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productFacadeImpl.add(productDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable Long id) { productFacadeImpl.delete(id); }

    @PutMapping("/update/{id}")
    public void updateProduct(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long id) {
        productFacadeImpl.update(productDTO, id);
    }
}

