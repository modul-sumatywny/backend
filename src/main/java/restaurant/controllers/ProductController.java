package restaurant.controllers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Dish;
import restaurant.model.Product;
import restaurant.model.dto.DishDto;
import restaurant.model.dto.ProductDto;
import restaurant.model.dto.ProductPostDto;
import restaurant.model.mapper.DishMapper;
import restaurant.model.mapper.ProductMapper;
import restaurant.service.ProductService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/products")
public class ProductController extends CrudController<Long, Product, ProductDto, ProductPostDto> {

    private final ProductService productService;
    private final DishMapper dishMapper = Mappers.getMapper(DishMapper.class);

    public ProductController(ProductService productService) {
        super(Mappers.getMapper(ProductMapper.class), productService);

        this.productService = productService;
    }

}