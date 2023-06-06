package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.model.DishProduct;
import restaurant.model.DishProductId;

import java.util.List;
import java.util.Optional;

@Repository
public interface DishProductRepository extends JpaRepository<DishProduct, DishProductId> {
    Optional<DishProduct> findById(DishProductId dishProductId);

    List<DishProduct> findByDish_Id(Long dishId);

    List<DishProduct> findByProduct_Id(Long productId);

    Optional<DishProduct> findByDish_IdAndProduct_Id(Long dishId, Long productId);

}
