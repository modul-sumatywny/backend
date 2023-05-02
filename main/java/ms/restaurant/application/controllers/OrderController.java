package ms.restaurant.application.controllers;

import jakarta.validation.Valid;
import ms.restaurant.application.dto.orderDto.OrderDTO;
import ms.restaurant.application.dto.orderDto.OrderStatusDTO;
import ms.restaurant.domain.facadeImpl.OrderFacadeImpl;
import ms.restaurant.domain.model.IDObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderFacadeImpl orderFacadeImpl;

    @Autowired
    public OrderController(OrderFacadeImpl orderFacadeImpl) {
        this.orderFacadeImpl = orderFacadeImpl;
    }

    @GetMapping("/get/{orderId}")
    public Optional<OrderDTO> getOrder(@PathVariable Long orderId) {
        return orderFacadeImpl.get(orderId);
    }

    @DeleteMapping("/delete/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderFacadeImpl.delete(orderId);
    }

    @PostMapping("/add")
    public IDObject addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        return orderFacadeImpl.add(orderDTO);
    }

//    @PutMapping("/update/{orderId}")
//    public void update(@Valid @RequestBody OrderDTO orderDTO, @PathVariable Long orderId) {
//        orderFacadeImpl.update(orderDTO, orderId);
//    }

    @PutMapping("/update/status/{orderId}")
    public void updateStatus(@Valid @RequestBody OrderStatusDTO orderStatusDTO, @PathVariable Long orderId) {
        orderFacadeImpl.updateStatus(orderStatusDTO, orderId);
    }
}
