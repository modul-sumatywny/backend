package restaurant.service;

import restaurant.model.Order;
import restaurant.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends CrudService<Long, Order> {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        super(orderRepository, Order.class);

        this.orderRepository = orderRepository;
    }
}
