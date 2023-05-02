package ms.restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.orderDto.OrderDTO;
import ms.restaurant.application.dto.orderDto.OrderStatusDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.facade.OrderFacade;
import ms.restaurant.domain.model.*;
import ms.restaurant.infrastructure.repository.DishRepository;
import ms.restaurant.infrastructure.repository.OrderRepository;
import ms.restaurant.infrastructure.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderFacadeImpl implements CRUDFacade<OrderDTO>, OrderFacade{
    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<OrderDTO> get(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return Optional.ofNullable(toOrderDTO(order));
    }

    @Override
    public void delete(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(OrderDTO orderDTO, Long id) {

    }

//    @Override
//    public void update(OrderDTO orderDTO, Long id) {
//        Order existingOrder = orderRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this ID doesnt exists in database!"));
//
//        if (orderRepository.existsById(id)) {
//            existingOrder.setId(id);
//            existingOrder.setDishes(orderDTO.getDishes());
//
//            Restaurant updatedRestaurant = restaurantRepository.findById(orderDTO.getRestaurantId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database"));
//            existingOrder.setRestaurant(updatedRestaurant);
//
//            orderRepository.save(existingOrder);
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Order just got updated");
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this ID doesn't exist in database");
//        }
//    }

    @Override
    @Transactional
    public IDObject add(OrderDTO orderDTO) {
        Order newOrder = new Order();

        Restaurant existingRestaurant = restaurantRepository.findById(orderDTO.getRestaurantId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database"));
        newOrder.setRestaurant(existingRestaurant);
        newOrder.setOrderTime(LocalDateTime.now());
        newOrder.setOrderStatus(Order.OrderStatus.PLACED);

        List<Dish> dishes = new ArrayList<>();
        for (Long dishId : orderDTO.getDishesIDs()) {
            Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with ID " + dishId + " doesn't exist in database"));
//            dishes.add(dish);
            boolean found = false;
            for (Menu menu : existingRestaurant.getMenus()) {
                if (menu.getDishes().contains(dish)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with ID " + dishId + " is not available in any menu of this restaurant");
            }

            dishes.add(dish);
        }
        newOrder.setDishes(dishes);
        orderRepository.save(newOrder);

        return new IDObject(newOrder.getId());
    }

    @Override
    public void updateStatus(OrderStatusDTO orderStatusDTO, Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with this ID doesn't exist in database"));
        order.setOrderStatus(Order.OrderStatus.valueOf(orderStatusDTO.getOrderStatus()));
        order.setId(orderId);
        orderRepository.save(order);
    }

    public OrderDTO toOrderDTO(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order toOrderFromDTO(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
}
