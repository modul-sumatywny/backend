package restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import restaurant.model.Dish;
import restaurant.model.Menu;
import restaurant.model.Order;
import restaurant.model.Product;
import restaurant.model.dto.DishDto;
import restaurant.model.dto.DishPostDto;

import java.util.Map;
import java.util.stream.Collectors;

@Mapper
public interface DishMapper extends MapperBase<Dish, DishDto, DishPostDto> {

    @Override
    default Dish postDtoToEntity(DishPostDto postDto) {
        return Dish.builder()
                .name(postDto.getName())
                .category(postDto.getCategory())
                .price(postDto.getPrice())
                .products(postDto.getQuantitiesWithProductIds().entrySet().stream()
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                entry -> Product.builder().id(entry.getValue()).build()
                        )))
                .build();
    }
}
