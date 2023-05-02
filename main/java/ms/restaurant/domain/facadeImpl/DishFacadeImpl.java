package ms.restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.dishDto.DishDTO;
import ms.restaurant.application.dto.productDto.ProductDTO;
import ms.restaurant.application.dto.productDto.ProductEanDto;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.model.Dish;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Product;
import ms.restaurant.infrastructure.repository.DishRepository;
import ms.restaurant.infrastructure.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class DishFacadeImpl implements CRUDFacade<DishDTO> {
    private final DishRepository dishRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<DishDTO> get(Long id) {
        Dish dish = dishRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish not found"));
        return Optional.ofNullable(toDishDTO(dish));
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
        Dish existingDish = dishRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesnt exists in database!"));

        if (dishRepository.existsById(id)) {
            existingDish.setId(id);
            existingDish.setName(dishDTO.getName());
            existingDish.setPrice(dishDTO.getPrice());
            existingDish.setCategory(dishDTO.getCategory());
            dishRepository.save(existingDish);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Dish just got updated");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesn't exist in database");
        }
    }



    //to jest raczej niepotrzebne. Bez sensu dodawac danie, ktore nie jest przypisane do zadnego menu
//    @Override
//    @Transactional
//    public IDObject add(DishDTO dishDTO) {
//        Dish dish = toDishFromDTO(dishDTO);
////        if(!dishRepository.existsByName(dish.getName())) {
////            dishRepository.save(dish);
////            productRepository.saveAll(dish.getProducts());
////        } else {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dish with this name already exists in database!");
////        }
//
//        dishRepository.save(dish);
//        productRepository.saveAll(dish.getProducts());
//        return new IDObject(dish.getId());
//    }

    @Transactional
    public void addProductToDish(ProductDTO productDTO, Long id) {
        Product product = toProductFromDTO(productDTO);
        Dish dish = dishRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesnt exists in database!"));

        boolean flag = false;
        for (Product productsFromList : dish.getProducts()) {
            if (productsFromList.getEan().equals(product.getEan())) {
                flag = true;
            }
        }

        if (!flag) {
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

    @Override
    public IDObject add(DishDTO dishDTO) {
        return null;
    }

    public Product toProductFromDTO(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }

    public ProductDTO toProductDTO(Optional<Product> product) {
        return modelMapper.map(product, ProductDTO.class);
    }


    public Dish toDishFromDTO(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    public DishDTO toDishDTO(Dish dish) {
        return modelMapper.map(dish, DishDTO.class);
    }
}
