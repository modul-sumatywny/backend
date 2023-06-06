package restaurant.service;

import org.springframework.stereotype.Service;
import restaurant.exception.EntityNotFoundException;
import restaurant.model.DishProduct;
import restaurant.repository.DishProductRepository;

import java.util.List;

@Service
public class DishProductService {
    private final DishProductRepository dishProductRepository;
    Class<DishProduct> type;

    public DishProductService(DishProductRepository dishProductRepository) {
        this.dishProductRepository = dishProductRepository;
        type = DishProduct.class;
    }

    public List<DishProduct> getAll() {
        return dishProductRepository
                .findAll();
    }

    public DishProduct create(DishProduct entity) {
        return dishProductRepository
                .save(entity);
    }

    public DishProduct update(DishProduct entity) {
        return dishProductRepository
                .save(entity);
    }

    public DishProduct getByProductIdAndDishId(Long productId, Long dishId) {
        DishProduct entity = dishProductRepository.findByDish_IdAndProduct_Id(productId, dishId).orElseThrow(()
                -> new EntityNotFoundException(type, "id", productId, dishId));
        return entity;
    }

    public  List<DishProduct> getByProductId(Long productId) {
        List<DishProduct> entities = dishProductRepository.findByProduct_Id(productId);
        return entities;
    }
    public  List<DishProduct> getByDishId(Long dishId) {
        List<DishProduct> entities = dishProductRepository.findByDish_Id(dishId);
        return entities;
    }

    public DishProduct delete(Long productId, Long dishId) {
        DishProduct entity = dishProductRepository.findByDish_IdAndProduct_Id(dishId, productId).orElseThrow(()
                -> new EntityNotFoundException(type, "id", productId, dishId));

        dishProductRepository.delete(entity);
        return entity;
    }
}
