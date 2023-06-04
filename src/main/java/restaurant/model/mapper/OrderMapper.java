package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Account;
import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.Restaurant;
import restaurant.model.dto.OrderDto;
import restaurant.model.dto.OrderPostDto;


@Mapper
public interface OrderMapper extends MapperBase<Order, OrderDto, OrderPostDto> {

    @Override
    default Order postDtoToEntity(OrderPostDto postDto){
        return Order.builder()
                .orderTime(postDto.getOrderTime())
                .lastName(postDto.getLastName())
                .firstName(postDto.getFirstName())
                .phoneNumber(postDto.getPhoneNumber())
                .account(Account.builder()
                        .id(postDto.getAccountId())
                        .build())
                .restaurant(Restaurant.builder()
                        .id(postDto.getRestaurantId())
                        .build())
                .dishes(postDto.getDishesIDs().stream()
                        .map(id -> Dish.builder()
                                .id(id)
                                .build())
                        .toList())
                .build();
    }
}
