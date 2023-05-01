package ms.restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.infrastructure.repository.DishRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ms.restaurant.application.dto.productDTO.ProductDTO;
import ms.restaurant.domain.facade.ProductFacade;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Product;
import ms.restaurant.infrastructure.repository.ProductRepository;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductFacadeImpl implements CRUDFacade<ProductDTO>, ProductFacade {
    private final ProductRepository productRepository;
    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;

    public Product toProductFromDTO(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toProductDTO(Optional<Product> product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Optional<ProductDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(ProductDTO productDTO, Long id) {
        if (productRepository.existsById(id)) {
            Product updatedProduct = toProductFromDTO(productDTO);
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
        }
    }

    @Override
    public IDObject add(ProductDTO productDTO) {
        return null;
    }

    public ResponseEntity<Map<String, String>> getProduct(Long id) {
        if (productRepository.existsById(id)) {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                Map<String, String> responseMap = new LinkedHashMap<>();
                responseMap.put("name", product.get().getName());
                responseMap.put("ean", product.get().getEan());
                return ResponseEntity.ok(responseMap);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
        }
    }
}

