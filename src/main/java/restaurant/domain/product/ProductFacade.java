package restaurant.domain.product;

import restaurant.application.dto.ProductDto;

public interface ProductFacade {
    ProductDto getProduct(long id);
}
