package ms.restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.dishDto.DishDTO;
import ms.restaurant.application.dto.menuDto.MenuDTO;
import ms.restaurant.application.dto.UpdateMenuDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.facade.MenuFacade;
import ms.restaurant.domain.model.Dish;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Menu;
import ms.restaurant.infrastructure.repository.DishRepository;
import ms.restaurant.infrastructure.repository.MenuRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MenuFacadeImpl implements CRUDFacade<MenuDTO>, MenuFacade {
    private final MenuRepository menuRepository;
    private final DishRepository dishRepository;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(MenuFacadeImpl.class);

    @Override
    public Optional<MenuDTO> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(MenuDTO menuDTO, Long id) {

    }

    public void update(UpdateMenuDTO updateMenuDTO, Long id) {
        Menu existingMenu = menuRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesnt exists in database!"));

        if (menuRepository.existsById(id)) {
            existingMenu.setId(id);
            existingMenu.setName(updateMenuDTO.getName());
            menuRepository.save(existingMenu);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Menu just got updated");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
        }
    }

    @Override
    @Transactional
    public IDObject add(MenuDTO menuDTO) {
        Menu menu = toMenuFromDTO(menuDTO);
        if(!menuRepository.existsByName(menu.getName())) {
            dishRepository.saveAll(menu.getDishes());
            menuRepository.save(menu);
            log.info("Menu updated: {}", menu);

        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu with this name already exists in database!");
        }
        return new IDObject(menu.getId());
    }

//    @Transactional
//    public void addDishToMenu(DishDTO dishDTO, Long id) {
//        Dish dish = toDishFromDTO(dishDTO);
//        Menu menu = menuRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesnt exists in database!"));
//
//        if (!dishRepository.existsByEan(product.getEan())) {
//            dish.getProducts().add(product);
//            dishRepository.save(dish);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This dish already consist product with this EAN");
//        }
////        return new IDObject(product.getId());
//    }

    @Override
    public ResponseEntity<Map<String, String>> getMenu(Long id) {
        if (menuRepository.existsById(id)) {
            Optional<Menu> menu = menuRepository.findById(id);
            if (menu.isPresent()) {
                Map<String, String> responseMap = new LinkedHashMap<>();
                responseMap.put("name", menu.get().getName());
                return ResponseEntity.ok(responseMap);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
        }
    }



//    @Override
//    public Optional<MenuDTO> get(Long id) {
//        Optional<Menu> menu = menuRepository.findById(id);
//        if (menu.isPresent()) {
//            return Optional.ofNullable(toMenuDTO(menu));
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//        if (menuRepository.existsById(id)) {
//            menuRepository.deleteById(id);
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
//        }
//    }
//
//    @Override
//    public void update(MenuDTO menuDTO, Long id) {
//
//    }
//
//    @Transactional
//    @Override
//    public IDObject add(MenuDTO menuDTO) {
//        for (Dish d : menuDTO.getDishes()) {
////            if (!dishRepository.existsByName(d.getName())) {
////                DishDTO newDish = toDishDTO(Optional.ofNullable(d));
////                dishFacadeImpl.add(newDish);
////            }
//            Optional<Dish> existingDish = dishRepository.findByName(d.getName());
//            if (existingDish.isPresent()) {
//                d.setId(existingDish.get().getId());
//            } else {
//                DishDTO newDish = toDishDTO(Optional.ofNullable(d));
//                dishFacadeImpl.addDishWithGivenID(newDish, d.getId());
//            }
//        }
//        Menu menu = toMenuFromDTO(menuDTO);
//        if (!menuRepository.existsByName(menu.getName())) {
//            menuRepository.save(menu);
//        } else {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu with this name already exists in database!");
//
//        }
////        if(!menuRepository.existsById(menu.getId())) {
////            menuRepository.save(menu);
////        } else {
////            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Menu with this id already exists in database!");
////        }
//        return new IDObject(menu.getId());
//    }
//
//    public List<Dish> getDishesByMenuId(Long id) {
//        Optional<Menu> menu = menuRepository.findById(id);
//        if (menu.isPresent()) {
//            return menu.get().getDishes();
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
//        }
//    }
//
//    public DishDTO addDishToMenu(Long id, DishDTO dishDTO) {
//        Optional<Menu> menu = menuRepository.findById(id);
//        if (menu.isPresent()) {
//            Menu updatedMenu = menu.get();
//            List<Dish> dishes = updatedMenu.getDishes();
//            dishes.add(toDishFromDTO(dishDTO));
//            updatedMenu.setDishes(dishes);
//            dishFacadeImpl.add(dishDTO);
//            return toDishDTO(Optional.ofNullable(menuRepository.save(updatedMenu).getDishes().get(dishes.size() - 1)));
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
//        }
//    }
//
//    public void removeDishFromMenu(Long menuId, Long dishId) {
//        Optional<Menu> menu = menuRepository.findById(menuId);
//        if (menu.isPresent()) {
//            Menu updatedMenu = menu.get();
//            List<Dish> dishes = updatedMenu.getDishes();
//            dishes.removeIf(dish -> dish.getId().equals(dishId));
//            updatedMenu.setDishes(dishes);
//            dishFacadeImpl.delete(dishId);
//            menuRepository.save(updatedMenu);
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesn't exist in database");
//        }
//    }

    public Dish toDishFromDTO(DishDTO dishDTO) {
        return modelMapper.map(dishDTO, Dish.class);
    }

    public DishDTO toDishDTO(Optional<Dish> dish) {
        return modelMapper.map(dish, DishDTO.class);
    }

    public Menu toMenuFromDTO(MenuDTO menuDTO) {
        return modelMapper.map(menuDTO, Menu.class);
    }

    public MenuDTO toMenuDTO(Optional<Menu> menu) {
        return modelMapper.map(menu, MenuDTO.class);
    }


}
