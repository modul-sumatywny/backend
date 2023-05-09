package restaurant.application.controllers;


import jakarta.validation.Valid;
import restaurant.application.dto.dishDto.DishDTO;
import restaurant.application.dto.dishDto.DishNameDTO;
import restaurant.application.dto.menuDto.MenuDTO;
import restaurant.application.dto.menuDto.MenuWithIdDTO;
import restaurant.application.dto.restaurantTableDto.RestaurantTableWithIdDTO;
import restaurant.domain.facadeImpl.MenuFacadeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/menu")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MenuController {
    private final MenuFacadeImpl menuFacadeImpl;

    @Autowired
    public MenuController(MenuFacadeImpl menuFacadeImpl) {
        this.menuFacadeImpl = menuFacadeImpl;
    }

    @GetMapping("/{menuId}")
    public Optional<MenuDTO> getMenu(@PathVariable Long menuId) {
        return menuFacadeImpl.get(menuId);
    }

    //    @PostMapping("/addDish")
//    public DishDTO addDishToMenu(@PathVariable Long id, @Valid @RequestBody DishDTO dishDTO) {
//        return menuFacadeImpl.addDishToMenu(id, dishDTO);
//    }
    @PutMapping("/update/{menuId}")
    public void updateMenu(@Valid @RequestBody MenuDTO menuDTO, @PathVariable Long menuId) {
        menuFacadeImpl.update(menuDTO, menuId);
    }

    //raczej niepotrzebne bo nie bedziemy dodawac bez przypisania menu go do restauracji
//    @PostMapping("/add")
//    public IDObject addMenu(@Valid @RequestBody MenuDTO menuDTO) {
//        return menuFacadeImpl.add(menuDTO);
//    }

    @PostMapping("/add/dish/{menuId}")
    public void addDishToMenu(@Valid @RequestBody DishDTO dishDTO, @PathVariable Long menuId) {
        menuFacadeImpl.addDishToMenu(dishDTO, menuId);
    }

    @DeleteMapping("/delete/dish/{menuId}")
    public void deleteDishFromMenu(@RequestBody DishNameDTO dishNameDTO, @PathVariable Long menuId) {
        menuFacadeImpl.deleteDishFromMenu(dishNameDTO, menuId);
    }

    @GetMapping("/allMenus")
    public List<MenuWithIdDTO> getAllMenus() {
        return menuFacadeImpl.getAll();
    }

}
