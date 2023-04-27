package restaurant.infrastracture.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.domain.product.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
