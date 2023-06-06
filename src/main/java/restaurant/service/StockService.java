package restaurant.service;

import org.springframework.stereotype.Service;
import restaurant.model.Stock;
import restaurant.repository.StockRepository;

import java.util.List;

@Service
public class StockService extends CrudService<Long, Stock> {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        super(stockRepository, Stock.class);

        this.stockRepository = stockRepository;
    }

    public List<Stock> getStocksByRestaurantId(Long restaurantId) {
        return stockRepository.findStocksByRestaurantId(restaurantId);
    }

    public List<Stock> getStocksByProductId(Long productId) {
        return stockRepository.findStocksByProductId(productId);
    }
}
