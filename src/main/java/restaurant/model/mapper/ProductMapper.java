package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Dish;
import restaurant.model.Product;
import restaurant.model.dto.DishPostDto;
import restaurant.model.dto.ProductDto;
import restaurant.model.dto.ProductPostDto;


@Mapper
public interface ProductMapper extends MapperBase<Product, ProductDto, ProductPostDto> {
    
    @Override
    default Product postDtoToEntity(ProductPostDto postDto) {
        return Product.builder()
                .name(postDto.getName())
                .ean(postDto.getEan())
                .dishes(postDto.getDishesIds().stream()
                        .map(id -> Dish.builder()
                                .id(id)
                                .build())
                        .toList())
                .build();
    }
}
