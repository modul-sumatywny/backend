package restaurant.application.controllers;


import jakarta.validation.Valid;
import restaurant.application.dto.dishDto.DishDTO;
import restaurant.application.dto.dishDto.DishWithIdDTO;
import restaurant.application.dto.productDto.ProductDTO;
import restaurant.application.dto.productDto.ProductEanDto;
import restaurant.application.dto.restaurantTableDto.RestaurantTableWithIdDTO;
import restaurant.domain.facadeImpl.DishFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dishes")
public class DishController {
    private final DishFacadeImpl dishFacadeImpl;

    @Autowired
    public DishController(DishFacadeImpl dishFacadeImpl) {this.dishFacadeImpl = dishFacadeImpl;}

    @GetMapping("/{dishId}")
    public Optional<DishDTO> getDish(@PathVariable Long dishId) {
        return dishFacadeImpl.get(dishId);
    }

    //raczej niepotrzebne bo nie bedziemy dodawac bez przypisania go do menu
//    @PostMapping("/add")
//    public IDObject addDish(@Valid @RequestBody DishDTO dishDTO) {
//        return dishFacadeImpl.add(dishDTO);
//    }

    @DeleteMapping("/delete/{dishId}")
    public void deleteDish(@PathVariable Long dishId) { dishFacadeImpl.delete(dishId); }

    @PutMapping("/update/{dishId}")
    public void updateDish(@Valid @RequestBody DishDTO dishDTO, @PathVariable Long dishId) {
        dishFacadeImpl.update(dishDTO, dishId);
    }

    @PostMapping("/add/product/{dishId}")
    public void addProductToDish(@Valid @RequestBody ProductDTO productDTO, @PathVariable Long dishId) {
        dishFacadeImpl.addProductToDish(productDTO, dishId);
    }

    @DeleteMapping("/delete/product/{dishId}")
    public void deleteProductFromDish(@Valid @RequestBody ProductEanDto productEanDTO, @PathVariable Long dishId) {
        dishFacadeImpl.deleteProductFromDish(productEanDTO, dishId);
    }

    @GetMapping("/allDishes")
    public List<DishWithIdDTO> getAllDishes() {
        return dishFacadeImpl.getAll();
    }
}
