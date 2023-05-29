package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Order;
import restaurant.model.dto.OrderDto;
import restaurant.model.dto.OrderPostDto;
import restaurant.model.mapper.OrderMapper;
import restaurant.service.OrderService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/orders")
public class OrderController extends CrudController<Long, Order, OrderDto, OrderPostDto> {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        super(Mappers.getMapper(OrderMapper.class), orderService);

        this.orderService = orderService;
    }
}
