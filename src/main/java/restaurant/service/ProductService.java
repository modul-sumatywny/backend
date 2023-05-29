package restaurant.service;

import restaurant.model.Product;
import restaurant.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends CrudService<Long, Product> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        super(productRepository, Product.class);

        this.productRepository = productRepository;
    }
}
