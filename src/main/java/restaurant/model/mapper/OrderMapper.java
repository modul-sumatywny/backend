package restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
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

    @Override
    default OrderDto entityToDto(Order entity){
        return OrderDto.builder()
                .accountId(entity.getAccount().getId())
                .email(entity.getAccount().getEmail())
                .orderStatus(entity.getOrderStatus().name())
                .orderTotalCost(entity.getOrderTotalCost())
                .orderTime(entity.getOrderTime().toString())
                .dishes(entity.getDishes().stream().map(Mappers.getMapper(DishMapper.class)::entityToDto).toList())
                .firstName(entity.getFirstName())
                .id(entity.getId())
                .lastName(entity.getLastName())
                .phoneNumber(entity.getPhoneNumber())
                .restaurant(Mappers.getMapper(RestaurantMapper.class).entityToDto(entity.getRestaurant()))
                .build();
    }
}
