package ms.restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ms.restaurant.application.dto.ProductDTO;
import ms.restaurant.domain.facade.ProductFacade;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Product;
import ms.restaurant.infrastructure.repository.ProductRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductFacadeImpl implements ProductFacade {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Product toProductFromDTO(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toProductDTO(Optional<Product> product) {
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Optional<ProductDTO> getProduct(Long id) {
        if (productRepository.existsById(id)) {
            return Optional.ofNullable(toProductDTO(productRepository.findById(id)));
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
        }
    }

    @Override
    public IDObject addProduct(ProductDTO productDTO) {
        Product product = toProductFromDTO(productDTO);
        if(!productRepository.existsByEan(product.getEan())) {
            productRepository.save(product);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with this EAN already exists in database!");
        }

        return new IDObject(product.getId());
    }

    @Override
    public void deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
        }
    }

    @Override
    public void updateProduct(ProductDTO productDTO, Long id) {
        if (productRepository.existsById(id)) {
            Product updatedProduct = toProductFromDTO(productDTO);
            updatedProduct.setId(id);
            productRepository.save(updatedProduct);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
        }
    }
}

