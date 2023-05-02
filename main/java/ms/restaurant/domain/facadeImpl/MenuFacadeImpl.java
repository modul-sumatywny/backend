package ms.restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.dishDto.DishDTO;
import ms.restaurant.application.dto.menuDto.MenuDTO;
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

import java.util.*;

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
        Menu existingMenu = menuRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Dish with this ID doesnt exists in database!"));

        if (menuRepository.existsById(id)) {
            existingMenu.setId(id);
            existingMenu.setName(menuDTO.getName());
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

    @Transactional
    public void addDishToMenu(DishDTO dishDTO, Long id) {
        Dish dish = toDishFromDTO(dishDTO);
        Menu menu = menuRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesnt exists in database!"));

        boolean flag = false;
        for (Dish dishesFromList : menu.getDishes()) {
            if (dishesFromList.getName().equals(dish.getName())) {
                flag = true;
            }
        }

        if (!flag) {
            menu.getDishes().add(dish);
            menuRepository.save(menu);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This menu already have this dish with that name");
        }
//        return new IDObject(product.getId());
    }

    @Transactional
    public void deleteDishFromMenu(DishDTO dishDTO, Long dishId) {
        Menu menu = menuRepository.findById(dishId).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with this ID doesnt exists in database!"));
        List<Dish> dishListCopy = new ArrayList<>(menu.getDishes());

        boolean flag = false;
        for (Dish dishFromList : dishListCopy) {
            if (dishFromList.getName().equals(dishDTO.getName())) {
                menu.getDishes().remove(dishFromList);
                dishRepository.delete(dishFromList);
                menuRepository.save(menu);
                flag = true;
            }
        }

        if (!flag) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu with given ID doesnt have dish with given name" );
        }
    }

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
