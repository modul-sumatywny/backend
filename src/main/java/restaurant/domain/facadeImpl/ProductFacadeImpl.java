package restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import restaurant.application.dto.productDto.ProductWithIdDTO;
import restaurant.application.dto.restaurantTableDto.RestaurantTableWithIdDTO;
import restaurant.domain.facade.CRUDFacade;
import restaurant.domain.model.RestaurantTable;
import restaurant.infrastructure.repository.DishRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.application.dto.productDto.ProductDTO;
import restaurant.domain.model.IDObject;
import restaurant.domain.model.Product;
import restaurant.infrastructure.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductFacadeImpl implements CRUDFacade<ProductDTO, ProductWithIdDTO> {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Product toProductFromDTO(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toProductDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }

//    @Override
//    public Optional<ProductDTO> get(Long id) {
//        return Optional.empty();
//    }
    @Override
    public Optional<ProductDTO> get(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return Optional.ofNullable(toProductDTO(product));
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

    @Override
    public List<ProductWithIdDTO> getAll() {
        List<ProductWithIdDTO> products = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            ProductWithIdDTO productWithIdDTO = new ProductWithIdDTO();
            productWithIdDTO.setId(product.getId());
            productWithIdDTO.setName(product.getName());
            productWithIdDTO.setEan(product.getEan());
            products.add(productWithIdDTO);
        }
        return products;
    }
}

