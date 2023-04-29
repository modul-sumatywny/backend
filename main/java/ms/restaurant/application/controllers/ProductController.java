package ms.restaurant.application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ms.restaurant.application.dto.ProductDTO;
import ms.restaurant.domain.facade.ProductFacade;
import ms.restaurant.domain.model.IDObject;

import jakarta.validation.*;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductFacade productFacade;

    @Autowired
    public ProductController(ProductFacade productFacade) { this.productFacade = productFacade; }

    @GetMapping("/{id}")
    public Optional<ProductDTO> getProduct(@PathVariable Long id) {
        return productFacade.getProduct(id);
    }

    @PostMapping("/new")
    public IDObject addProduct(@Valid @RequestBody ProductDTO productDTO) {
        return productFacade.addProduct(productDTO);
    }
}

