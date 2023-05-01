package ms.restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.DishDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.model.Dish;
import ms.restaurant.infrastructure.repository.DishRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ms.restaurant.application.dto.ProductDTO;
import ms.restaurant.domain.facade.ProductFacade;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Product;
import ms.restaurant.infrastructure.repository.ProductRepository;

import java.util.HashMap;
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

//    @Override
//    public Optional<ProductDTO> get(Long id) {
//        if (productRepository.existsById(id)) {
//            Optional<Product> product = productRepository.findById(id);
//            if (product.isPresent()) {
//                ProductDTO productDTO = new ProductDTO();
//                //toProductDTO(product);
//                productDTO.setName(product.get().getName());
//                productDTO.setEan(product.get().getEan());
////                productDTO.setDishes(product.get().getDishes()); //tutaj wpada w nieskonczona petle po wysłaniu requesta na endpoint
//                return Optional.ofNullable(productDTO);
//            } else {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
//            }
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product with this ID doesn't exist in database");
//        }
//    }

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

//    @Override
//    public IDObject add(ProductDTO productDTO) {
//        return null;
//    }


    //metoda add ponizej jest do usuniecia. Tylko w przyszlosci bo trzeba wszystkie metody z interfejsu nadpisac
//    @Override
//    public IDObject add(ProductDTO productDTO) {
//        Product product = toProductFromDTO(productDTO);
//        if(!productRepository.existsByEan(product.getEan())) {
//            productRepository.save(product);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with this EAN already exists in database!");
//        }
//
//        return new IDObject(product.getId());
//    }


    //to tez do wydupcenia bo sie request zapetla
    @Override
    public IDObject add(ProductDTO productDTO) {
        Product product = toProductFromDTO(productDTO);
//        for (Dish dish : product.getDishes()) {
//            Optional<Dish> existingDish = dishRepository.findByName(dish.getName());
//            if (existingDish.isPresent()) {
//                dish.setId(existingDish.get().getId());
//            } else {
//                dishRepository.save(dish); // zapisujemy nowy produkt do bazy danych
//            }
//        }
        if(!productRepository.existsByEan(product.getEan())) {
            productRepository.save(product);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product with this name already exists in database!");
        }
        return new IDObject(product.getId());
    }

    public ResponseEntity<Map<String, String>> getProduct(Long id) {
        if (productRepository.existsById(id)) {
            Optional<Product> product = productRepository.findById(id);
            if (product.isPresent()) {
                Map<String, String> responseMap = new HashMap<>();
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

