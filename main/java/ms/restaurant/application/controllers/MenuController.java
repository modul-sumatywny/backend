package ms.restaurant.application.controllers;


import jakarta.validation.Valid;
import ms.restaurant.application.dto.dishDto.DishDTO;
import ms.restaurant.application.dto.menuDto.MenuDTO;
import ms.restaurant.domain.facadeImpl.MenuFacadeImpl;
import ms.restaurant.domain.model.IDObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {
    private final MenuFacadeImpl menuFacadeImpl;

    @Autowired
    public MenuController(MenuFacadeImpl menuFacadeImpl) {
        this.menuFacadeImpl = menuFacadeImpl;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getMenu(@PathVariable Long id) {
        return menuFacadeImpl.getMenu(id);
    }

//    @PostMapping("/addDish")
//    public DishDTO addDishToMenu(@PathVariable Long id, @Valid @RequestBody DishDTO dishDTO) {
//        return menuFacadeImpl.addDishToMenu(id, dishDTO);
//    }
    @PutMapping("/update/{id}")
    public void updateMenu(@Valid @RequestBody MenuDTO menuDTO, @PathVariable Long id) {
        menuFacadeImpl.update(menuDTO, id);
    }

    @PostMapping("/add")
    public IDObject addMenu(@Valid @RequestBody MenuDTO menuDTO) {
        return menuFacadeImpl.add(menuDTO);
    }

    @PostMapping("/add/dish/{menuId}")
    public void addDishToMenu(@Valid @RequestBody DishDTO dishDTO, @PathVariable Long menuId) {
        menuFacadeImpl.addDishToMenu(dishDTO, menuId);
    }

    @DeleteMapping("/delete/dish/{menuId}")
    public void deleteDishFromMenu(@RequestBody DishDTO dishDTO, @PathVariable Long menuId) {
        menuFacadeImpl.deleteDishFromMenu(dishDTO, menuId);
    }
}
