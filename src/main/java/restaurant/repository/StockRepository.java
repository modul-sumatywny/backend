package restaurant.repository;

import org.springframework.stereotype.Repository;
import restaurant.model.Stock;

import java.util.List;

@Repository
public interface StockRepository extends RepositoryBase<Stock, Long>{
    List<Stock> findStocksByRestaurantId(Long restaurantId);

    List<Stock> findStocksByProductId(Long productId);

}
