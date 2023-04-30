package ms.restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.DishDTO;
import ms.restaurant.application.dto.ProductDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.facade.DishFacade;
import ms.restaurant.domain.model.Dish;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Product;
import ms.restaurant.infrastructure.repository.DishRepository;
import ms.restaurant.infrastructure.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DishFacadeImpl implements CRUDFacade<DishDTO>, DishFacade {
    private final DishRepository dishRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public Dish toDishFromDTO(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    public DishDTO toDishDTO(Optional<Dish> dish) {
        return modelMapper.map(dish, DishDTO.class);
    }


//    @Override
//    public Optional<DishDTO> get(Long id) {
//        if (dishRepository.existsById(id)) {
//            return Optional.ofNullable(toDishDTO(dishRepository.findById(id)));
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesn't exist in database");
//        }
//    }

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
        if (dishRepository.existsById(id)) {
            Dish updatedDish = toDishFromDTO(dishDTO);
            updatedDish.setId(id);
            dishRepository.save(updatedDish);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesn't exist in database");
        }
    }

//    @Override
//    public IDObject add(DishDTO dishDTO) {
//        Dish dish = toDishFromDTO(dishDTO);
//        if(!dishRepository.existsByName(dish.getName())) {
//
//            dishRepository.save(dish);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with this name already exists in database!");
//        }
//
//        return new IDObject(dish.getId());
//    }

    public IDObject add(DishDTO dishDTO) {
        Dish dish = toDishFromDTO(dishDTO);
        for (Product product : dish.getProducts()) {
            Optional<Product> existingProduct = productRepository.findByEan(product.getEan());
            if (existingProduct.isPresent()) {
                product.setId(existingProduct.get().getId());
            } else {
                productRepository.save(product); // zapisujemy nowy produkt do bazy danych
            }
        }
        if(!dishRepository.existsByName(dish.getName())) {
            dishRepository.save(dish);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with this name already exists in database!");
        }
        return new IDObject(dish.getId());
    }

    @Override
    public ResponseEntity<Map<String, String>> getDish(Long id) {
        if (dishRepository.existsById(id)) {
            Optional<Dish> dish = dishRepository.findById(id);
            if (dish.isPresent()) {
                Map<String, String> responseMap = new HashMap<>();
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
}
