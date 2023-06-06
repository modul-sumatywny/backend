package restaurant.repository;

import restaurant.model.Stock;

import java.util.List;

public interface StockRepository extends RepositoryBase<Stock, Long>{
    List<Stock> findStocksByRestaurantId(Long restaurantId);

    List<Stock> findStocksByProductId(Long productId);

}
