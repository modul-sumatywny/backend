package ms.restaurant.application.controllers;


import jakarta.validation.Valid;
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

    @PostMapping("/add")
    public IDObject addMenu(@Valid @RequestBody MenuDTO menuDTO) {
        return menuFacadeImpl.add(menuDTO);
    }
}
