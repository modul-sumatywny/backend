package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Dish;
import restaurant.model.Restaurant;
import restaurant.model.Table;
import restaurant.model.dto.DishPostDto;
import restaurant.model.dto.TableDto;
import restaurant.model.dto.TablePostDto;

@Mapper
public interface TableMapper extends MapperBase<Table, TableDto, TablePostDto> {

    @Override
    default Table postDtoToEntity(TablePostDto postDto) {
        return Table.builder()
                .tableNumber(postDto.getTableNumber())
                .numberOfSeats(postDto.getNumberOfSeats())
                .restaurant(Restaurant.builder()
                        .id(postDto.getRestaurantId())
                        .build())
                .build();
    }
}
