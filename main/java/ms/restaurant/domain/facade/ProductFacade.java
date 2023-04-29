package ms.restaurant.domain.facade;

import ms.restaurant.application.dto.ProductDTO;
import ms.restaurant.domain.model.IDObject;

import java.util.Optional;

public interface ProductFacade {
    Optional<ProductDTO> getProduct(Long id);
    void deleteProduct(Long id);
    void updateProduct(ProductDTO productDTO, Long id);
    IDObject addProduct(ProductDTO productDTO);
}
