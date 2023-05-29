package restaurant.repository;

import org.springframework.stereotype.Repository;
import restaurant.model.Product;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends RepositoryBase<Product, Long> {
}

