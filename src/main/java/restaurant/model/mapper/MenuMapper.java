package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Dish;
import restaurant.model.Menu;
import restaurant.model.dto.MenuDto;
import restaurant.model.dto.MenuPostDto;

@Mapper
public interface MenuMapper extends MapperBase<Menu, MenuDto, MenuPostDto> {

    @Override
    default Menu postDtoToEntity(MenuPostDto postDto) {
        return Menu.builder()
                .name(postDto.getName())
                .dishes(postDto.getDishesIds().stream()
                        .map(id -> Dish.builder()
                                .id(id)
                                .build())
                        .toList())
                .build();
    }
}
