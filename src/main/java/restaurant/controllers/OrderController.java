package restaurant.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Dish;
import restaurant.model.Order;
import restaurant.model.OrderStatus;
import restaurant.model.Product;
import restaurant.model.dto.OrderDto;
import restaurant.model.dto.OrderPostDto;
import restaurant.model.mapper.OrderMapper;
import restaurant.service.DishService;
import restaurant.service.OrderService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/orders")
public class OrderController extends CrudController<Long, Order, OrderDto, OrderPostDto> {

    private final OrderService orderService;
    private final DishService dishService;

    public OrderController(OrderService orderService, DishService dishService) {
        super(Mappers.getMapper(OrderMapper.class), orderService);

        this.orderService = orderService;
        this.dishService = dishService;
    }

    @Override
    @Transactional
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderDto> createTEntity(@RequestBody OrderPostDto entityPostDto) {
        try {
            Order order = mapper.postDtoToEntity(entityPostDto);
            order.setOrderStatus(OrderStatus.PLACED);
            order.setOrderTotalCost(dishService.getByIds(entityPostDto.getDishesIDs()).stream().mapToInt(Dish::getPrice).sum());
            return ok(mapper.entityToDto(orderService.create(order)));
        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
    }

    @Transactional
    @PutMapping("change-status/{orderId}")
    public ResponseEntity<Object> addProduct(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        try {
            Order order = orderService.getById(orderId);
            order.setOrderStatus(status);
            return ok(mapper.entityToDto(order));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
