package restaurant.controllers;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.ValidationException;
import org.mapstruct.factory.Mappers;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.exception.IncorrectStatusFlowException;
import restaurant.model.*;
import restaurant.model.dto.OrderDto;
import restaurant.model.dto.OrderPostDto;
import restaurant.model.dto.ReservationDto;
import restaurant.model.mapper.OrderMapper;
import restaurant.repository.StockRepository;
import restaurant.service.DishService;
import restaurant.service.OrderService;
import restaurant.service.StockService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/orders")
public class OrderController extends CrudController<Long, Order, OrderDto, OrderPostDto> {

    private final OrderService orderService;

    private final DishService dishService;

    private final StockService stockService;

    public OrderController(OrderService orderService, DishService dishService, StockService stockService) {
        super(Mappers.getMapper(OrderMapper.class), orderService);

        this.orderService = orderService;
        this.dishService = dishService;
        this.stockService = stockService;
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<List<OrderDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<OrderDto> getTEntityById(Long aLong) {
        return super.getTEntityById(aLong);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_ADMIN'})")
    public ResponseEntity<OrderDto> updateTEntity(Long aLong, OrderPostDto entityPostDto) {
        return super.updateTEntity(aLong, entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_ADMIN'})")
    public ResponseEntity<OrderDto> deleteTEntity(Long aLong) {
        return super.deleteTEntity(aLong);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyAuthority({'SCOPE_CLIENT'})")
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderDto> createTEntity(@RequestBody OrderPostDto entityPostDto) {
        try {
            Order order = mapper.postDtoToEntity(entityPostDto); // dodac dishe z menu
            order.setOrderStatus(OrderStatus.PLACED);
            Integer totalCost = entityPostDto.getDishesIDs().stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream()
                    .mapToInt(entry -> dishService.getById(entry.getKey()).getPrice() * entry.getValue().intValue())
                    .sum();
            order.setOrderTotalCost(totalCost);
            Order createdOrder = orderService.create(order);
            return ok(mapper.entityToDto(orderService.getById(createdOrder.getId())));
        } catch (Exception e) {
            throw new EntityNotFoundException(e);
        }
    }

    @Transactional
    @PreAuthorize("hasAnyAuthority({'SCOPE_CLIENT'})")
    @PutMapping("change-status/{orderId}")
    public ResponseEntity<Object> changeStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
        try {
            Order order = orderService.getById(orderId);
            if (checkStatusFlow(order.getOrderStatus(), status)) {
                if (status.equals(OrderStatus.IN_PROGRESS)) {
                    List<DishProduct> dishProducts = order.getDishes().stream()
                            .flatMap(dish -> dish.getDishProducts().stream())
                            .collect(Collectors.toList());
                    List<Stock> stocks = stockService.getStocksByRestaurantId(order.getRestaurant().getId());
                    for (DishProduct dishProduct : dishProducts) {
                        Stock stockToChange = stocks.stream().filter(stock -> stock.getProduct().getId().equals(dishProduct.getId().getProduct())).findFirst().orElse(null);
                        if (stockToChange != null && stockToChange.getIsEnabled()) {
                            if (stockToChange.getStock() - dishProduct.getQuantity() >= 0) {
                                stockToChange.setStock(stockToChange.getStock() - dishProduct.getQuantity());
                                stockService.update(stockToChange.getId(), stockToChange);
                            } else {
                                throw new ValidationException("There is no stock of " + dishProduct.getProduct().getName());
                            }
                        }
                    }
                }
            }
            order.setOrderStatus(status);
            return ok(mapper.entityToDto(order));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    private boolean checkStatusFlow(OrderStatus orderStatusFrom, OrderStatus orderStatusTo) {
        if (orderStatusFrom.equals(OrderStatus.PLACED)) {
            if (orderStatusTo.equals(OrderStatus.COMPLETED)) {
                throw new IncorrectStatusFlowException(orderStatusFrom, orderStatusTo);
            }
        }
        if (orderStatusFrom.equals(OrderStatus.IN_PROGRESS)) {
            if (orderStatusTo.equals(OrderStatus.PLACED)) {
                throw new IncorrectStatusFlowException(orderStatusFrom, orderStatusTo);
            }
        }
        if (orderStatusFrom.equals(OrderStatus.COMPLETED)) {
            if (orderStatusTo.equals(OrderStatus.PLACED) || orderStatusTo.equals(OrderStatus.IN_PROGRESS)) {
                throw new IncorrectStatusFlowException(orderStatusFrom, orderStatusTo);
            }
        }
        if (orderStatusFrom.equals(OrderStatus.CANCELLED)) {
            throw new IncorrectStatusFlowException(orderStatusFrom, orderStatusTo);
        }
        return true;
    }

    @PreAuthorize("hasAnyAuthority({'SCOPE_CLIENT'})")
    @GetMapping("account/{accountId}")
    public ResponseEntity<?> getOrdersForAccount(@PathVariable Long accountId) {
        try {
            List<Order> orders = orderService.getOrdersByAccountId(accountId);
            return ok(orders.stream().map(mapper::entityToDto).collect(Collectors.toList()));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
