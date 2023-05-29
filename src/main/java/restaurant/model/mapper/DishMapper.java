package restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import restaurant.model.Dish;
import restaurant.model.Menu;
import restaurant.model.Order;
import restaurant.model.Product;
import restaurant.model.dto.DishDto;
import restaurant.model.dto.DishPostDto;

@Mapper
public interface DishMapper extends MapperBase<Dish, DishDto, DishPostDto> {

    @Override
    default Dish postDtoToEntity(DishPostDto postDto) {
        return Dish.builder()
                .name(postDto.getName())
                .category(postDto.getCategory())
                .price(postDto.getPrice())
                .products(postDto.getProductsIds().stream()
                        .map(id -> Product.builder()
                                .id(id)
                                .build())
                        .toList())
                .menus(postDto.getMenusIds().stream()
                        .map(id -> Menu.builder()
                                .id(id)
                                .build())
                        .toList())
                .build();
    }
}
