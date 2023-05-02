package ms.restaurant.application.controllers;


import jakarta.validation.Valid;
import ms.restaurant.application.dto.dishDto.DishDTO;
import ms.restaurant.application.dto.productDto.ProductDTO;
import ms.restaurant.application.dto.productDto.ProductEanDto;
import ms.restaurant.domain.facadeImpl.DishFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
