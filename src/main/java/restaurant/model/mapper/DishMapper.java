package restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import restaurant.model.*;
import restaurant.model.dto.DishDto;
import restaurant.model.dto.DishPostDto;

import java.util.List;

@Mapper
public interface DishMapper extends MapperBase<Dish, DishDto, DishPostDto> {

    @Override
    default Dish postDtoToEntity(DishPostDto postDto) {
        List<DishProduct> dishProducts = postDto.getProductsWithQuantities().entrySet().stream()
                .map(entry -> {
                    Long productId = entry.getKey();
                    Integer quantity = entry.getValue();
                    Product product = Product.builder()
                            .id(productId)
                            .build();
                    return DishProduct.builder()
                            .product(product)
                            .quantity(quantity)
                            .build();
                })
                .toList();

        return Dish.builder()
                .name(postDto.getName())
                .category(postDto.getCategory())
                .price(postDto.getPrice())
                .dishProducts(dishProducts)
                .build();
    }
}
