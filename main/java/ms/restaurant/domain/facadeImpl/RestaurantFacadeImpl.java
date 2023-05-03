package ms.restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.menuDto.MenuDTO;
import ms.restaurant.application.dto.restaurantDto.RestaurantDTO;
import ms.restaurant.application.dto.restaurantTableDto.RestaurantTableDTO;
import ms.restaurant.application.dto.restaurantTableDto.TableNumberDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Menu;
import ms.restaurant.domain.model.Restaurant;
import ms.restaurant.domain.model.RestaurantTable;
import ms.restaurant.infrastructure.repository.MenuRepository;
import ms.restaurant.infrastructure.repository.RestaurantRepository;
import ms.restaurant.infrastructure.repository.RestaurantTableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RestaurantFacadeImpl implements CRUDFacade<RestaurantDTO> {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ModelMapper modelMapper;

//    @Override
//    public Optional<RestaurantDTO> get(Long id) {
//        return Optional.empty();
//    }

    @Override
    public Optional<RestaurantDTO> get(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant not found"));
        return Optional.ofNullable(toRestaurantDTO(restaurant));
    }

    @Override
    public void delete(Long id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(RestaurantDTO restaurantDTO, Long id) {
        Restaurant existingRestaurant = restaurantRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesnt exists in database!"));

        if (restaurantRepository.existsById(id)) {
            existingRestaurant.setId(id);
            existingRestaurant.setName(restaurantDTO.getName());
            existingRestaurant.setPhoneNumber(restaurantDTO.getPhoneNumber());
            existingRestaurant.setAddress(restaurantDTO.getAddress());
            restaurantRepository.save(existingRestaurant);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Restaurant just got updated");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
        }
    }

    @Override
    @Transactional
    public IDObject add(RestaurantDTO restaurantDTO) {
        Restaurant restaurant = toRestaurantFromDTO(restaurantDTO);
        if(!restaurantRepository.existsByAddress(restaurant.getAddress())) {
            //menuRepository.saveAll(restaurant.getMenus());
            restaurantRepository.save(restaurant);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant with address name already exists in database!");
        }
        return new IDObject(restaurant.getId());
    }

    @Transactional
    public void addMenuToRestaurant(MenuDTO menuDTO, Long id) {
        Menu menu = toMenuFromDTO(menuDTO);
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesnt exists in database!"));

        boolean flag = false;
        for (Menu menuFromList : restaurant.getMenus()) {
            if (menuFromList.getName().equals(menu.getName())) {
                flag = true;
            }
        }

        if (!flag) {
            restaurant.getMenus().add(menu);
            restaurantRepository.save(restaurant);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This restaurant already have this menu with that name");
        }
      //  return new IDObject(menu.getId());
    }

    @Transactional
    public void deleteMenuFromRestaurant(MenuDTO menuDTO, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesnt exists in database!"));
        List<Menu> menuListCopy = new ArrayList<>(restaurant.getMenus());

        boolean flag = false;
        for (Menu menuFromList : menuListCopy) {
            if (menuFromList.getName().equals(menuDTO.getName())) {
                restaurant.getMenus().remove(menuFromList);
                menuRepository.delete(menuFromList);
                restaurantRepository.save(restaurant);
                flag = true;
            }
        }

        if (!flag) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with given ID doesnt have menu with given name" );
        }
    }

    @Transactional
    public void addTableToRestaurant(RestaurantTableDTO restaurantTableDTO, Long id) {
        RestaurantTable restaurantTable = toTableFromDTO(restaurantTableDTO);
        Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesnt exists in database!"));

        boolean flag = false;
        for (RestaurantTable restaurantTableFromList : restaurant.getRestaurantTables()) {
            if (restaurantTableFromList.getTableNumber().equals(restaurantTable.getTableNumber())) {
                flag = true;
            }
        }

        if (!flag) {
            restaurant.getRestaurantTables().add(restaurantTable);
            restaurantRepository.save(restaurant);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This restaurant already have this table with that exact number");
        }
        //  return new IDObject(menu.getId());
    }

    @Transactional
    public void deleteTableFromRestaurant(TableNumberDTO tableNumberDTO, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesnt exists in database!"));
        List<RestaurantTable> restaurantTableListCopy = new ArrayList<>(restaurant.getRestaurantTables());

        boolean flag = false;
        for (RestaurantTable restaurantTableFromList : restaurantTableListCopy) {
            if (restaurantTableFromList.getTableNumber().equals(tableNumberDTO.getTableNumber())) {
                restaurant.getRestaurantTables().remove(restaurantTableFromList);
                restaurantTableRepository.delete(restaurantTableFromList);
                restaurantRepository.save(restaurant);
                flag = true;
            }
        }

        if (!flag) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with given ID doesnt have table with given table number" );
        }
    }

    public List<Menu> getAllMenus(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesnt exists in database!"));
        return restaurant.getMenus();
    }

    public List<RestaurantTable> getAllRestaurantTables(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesnt exists in database!"));
        return restaurant.getRestaurantTables();
    }

    public Restaurant toRestaurantFromDTO(RestaurantDTO restaurantDTO) {
        return modelMapper.map(restaurantDTO, Restaurant.class);
    }

    public RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public Menu toMenuFromDTO(MenuDTO menuDTO) {
        return modelMapper.map(menuDTO, Menu.class);
    }

    public RestaurantTable toTableFromDTO(RestaurantTableDTO restaurantTableDTO) {
        return modelMapper.map(restaurantTableDTO, RestaurantTable.class);
    }
}
