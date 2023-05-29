package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.Restaurant;
import restaurant.model.Table;
import restaurant.model.dto.DishPostDto;
import restaurant.model.dto.RestaurantDto;
import restaurant.model.dto.RestaurantPostDto;


@Mapper
public interface RestaurantMapper extends MapperBase<Restaurant, RestaurantDto, RestaurantPostDto> {


    @Override
    default Restaurant postDtoToEntity(RestaurantPostDto postDto) {
        return Restaurant.builder()
                .name(postDto.getName())
                .phoneNumber(postDto.getPhoneNumber())
                .address(postDto.getAddress())
                .photo(postDto.getPhoto())
                .tables(postDto.getTablesIds().stream()
                        .map(id -> Table.builder()
                                .id(id)
                                .build())
                        .toList())
                .build();
    }
}
