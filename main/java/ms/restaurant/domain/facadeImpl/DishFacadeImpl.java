package ms.restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.dishDTO.DishDTO;
import ms.restaurant.application.dto.dishDTO.UpdateDishDTO;
import ms.restaurant.application.dto.productDTO.ProductDTO;
import ms.restaurant.application.dto.productDTO.ProductEanDto;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.facade.DishFacade;
import ms.restaurant.domain.model.Dish;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Product;
import ms.restaurant.infrastructure.repository.DishRepository;
import ms.restaurant.infrastructure.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DishFacadeImpl implements CRUDFacade<DishDTO>, DishFacade {
    private final DishRepository dishRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Dish toDishFromDTO(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    public DishDTO toDishDTO(Dish dish) {
        return modelMapper.map(dish, DishDTO.class);
    }

    @Override
    public Optional<DishDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        if (dishRepository.existsById(id)) {
            dishRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(DishDTO dishDTO, Long id) {

    }
    public void update(UpdateDishDTO updateDishDTO, Long id) {
        Dish existingDish = dishRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesnt exists in database!"));

        if (dishRepository.existsById(id)) {
            existingDish.setId(id);
            existingDish.setName(updateDishDTO.getName());
            existingDish.setPrice(updateDishDTO.getPrice());
            dishRepository.save(existingDish);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Dish just got updated");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesn't exist in database");
        }
    }

    @Transactional
    public IDObject add(DishDTO dishDTO) {
        Dish dish = toDishFromDTO(dishDTO);
        if(!dishRepository.existsByName(dish.getName())) {
            dishRepository.save(dish);
            productRepository.saveAll(dish.getProducts());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with this name already exists in database!");
        }
        return new IDObject(dish.getId());
    }

    @Transactional
    public void addProductToDish(ProductDTO productDTO, Long id) {
        Product product = toProductFromDTO(productDTO);
        Dish dish = dishRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesnt exists in database!"));

        if (!productRepository.existsByEan(product.getEan())) {
            dish.getProducts().add(product);
            dishRepository.save(dish);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This dish already consist product with this EAN");
        }
//        return new IDObject(product.getId());
    }

    @Transactional
    public void deleteProductFromDish(ProductEanDto productEanDTO, Long dishId) {
        Dish dish = dishRepository.findById(dishId).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesnt exists in database!"));
        List<Product> productListCopy = new ArrayList<>(dish.getProducts());

        boolean flag = false;
        for (Product productFromList : productListCopy) {
            if (productFromList.getEan().equals(productEanDTO.getEan())) {
                dish.getProducts().remove(productFromList);
                productRepository.delete(productFromList);
                dishRepository.save(dish);
                flag = true;
            }
        }

        if (!flag) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with given ID doesnt have product with given EAN" );
        }
    }

    @Transactional
    public void addDishWithGivenID(DishDTO dishDTO, Long id) {
        Dish dish = toDishFromDTO(dishDTO);
        dish.setId(id);

        for (Product product : dish.getProducts()) {
            Optional<Product> existingProduct = productRepository.findByEan(product.getEan());
            if (existingProduct.isPresent()) {
                product.setId(existingProduct.get().getId());
            } else {
                productRepository.save(product);
            }
        }

        if(!dishRepository.existsByName(dish.getName())) {
            dishRepository.save(dish);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with this name already exists in database!");
        }
    }



    @Override
    public ResponseEntity<Map<String, String>> getDish(Long id) {
        if (dishRepository.existsById(id)) {
            Optional<Dish> dish = dishRepository.findById(id);
            if (dish.isPresent()) {
                Map<String, String> responseMap = new LinkedHashMap<>();
                responseMap.put("name", dish.get().getName());
                responseMap.put("price", dish.get().getPrice().toString()); //int zwracany jako String
                return ResponseEntity.ok(responseMap);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesn't exist in database");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesn't exist in database");
        }
    }

    public Product toProductFromDTO(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toProductDTO(Optional<Product> product) {
        return modelMapper.map(product, ProductDTO.class);
    }

}
