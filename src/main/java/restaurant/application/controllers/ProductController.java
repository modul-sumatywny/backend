package restaurant.application.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.application.dto.ProductDto;
import restaurant.domain.product.ProductFacade;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductFacade productFacade;

    @GetMapping("/{id}")
    public ProductDto getProduct(@PathVariable long id) {
        return productFacade.getProduct(id);
    }
}
