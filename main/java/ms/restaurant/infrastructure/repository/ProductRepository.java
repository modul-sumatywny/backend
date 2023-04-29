package ms.restaurant.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ms.restaurant.domain.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByEan(String ean);
}

