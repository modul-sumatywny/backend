package restaurant.infrastracture.product.facade;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import restaurant.application.dto.ProductDto;
import restaurant.domain.product.ProductFacade;
import restaurant.domain.product.model.Product;
import restaurant.infrastracture.product.repository.ProductRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductFacadeImpl implements ProductFacade {
    private final ProductRepository productRepository;

    @Override
    public ProductDto getProduct(long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no prudct with that id"));
        return toProduct(product);
    }

    private ProductDto toProduct(final Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .image(product.getImage())
                .name(product.getName())
                .build();
    }
}
