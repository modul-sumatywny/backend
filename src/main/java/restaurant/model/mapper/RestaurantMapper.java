package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.*;
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
                .menu(Menu.builder()
                        .id(postDto.getMenuId())
                        .build())
                .build();
    }

}
