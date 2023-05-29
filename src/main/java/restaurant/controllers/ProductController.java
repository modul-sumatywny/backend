package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Product;
import restaurant.model.dto.ProductDto;
import restaurant.model.dto.ProductPostDto;
import restaurant.model.mapper.ProductMapper;
import restaurant.service.ProductService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/products")
public class ProductController extends CrudController<Long, Product, ProductDto, ProductPostDto> {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        super(Mappers.getMapper(ProductMapper.class), productService);

        this.productService = productService;
    }
}