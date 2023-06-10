package restaurant.repository;

import restaurant.model.Order;

import java.util.List;

public interface OrderRepository extends RepositoryBase<Order, Long> {
    List<Order> getOrdersByAccount_Id(Long accountId);
}
