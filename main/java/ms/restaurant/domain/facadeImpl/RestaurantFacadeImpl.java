package ms.restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.dishDto.DishDTO;
import ms.restaurant.application.dto.menuDto.MenuDTO;
import ms.restaurant.application.dto.menuDto.RestaurantDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.facade.RestaurantFacade;
import ms.restaurant.domain.model.Dish;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Menu;
import ms.restaurant.domain.model.Restaurant;
import ms.restaurant.infrastructure.repository.MenuRepository;
import ms.restaurant.infrastructure.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RequiredArgsConstructor
@Service
public class RestaurantFacadeImpl implements CRUDFacade<RestaurantDTO> {
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
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

//    @Override
//    public ResponseEntity<Map<String, String>> getRestaurant(Long id) {
//        if (restaurantRepository.existsById(id)) {
//            Optional<Restaurant> restaurant = restaurantRepository.findById(id);
//            if (restaurant.isPresent()) {
//                Map<String, String> responseMap = new LinkedHashMap<>();
//                responseMap.put("name", restaurant.get().getName());
//                responseMap.put("phoneNumber", String.valueOf(restaurant.get().getPhoneNumber())); //phoneNumber konwertowany do Stringa jak cos
//                responseMap.put("address", restaurant.get().getAddress());
//
//                return ResponseEntity.ok(responseMap);
//            } else {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
//            }
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
//        }
//    }

//    @Override
//    public Optional<RestaurantDTO> get(Long id) {
//        if (restaurantRepository.existsById(id)) {
//            Optional<Restaurant> restaurant = restaurantRepository.findById(id);
//            if (restaurant.isPresent()) {
//                return Optional.ofNullable(toRestaurantDTO(restaurant));
//            } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
//            }
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
//        }
//    }
//
//    @Override
//    public void delete(Long id) {
//
//    }
//
//    @Override
//    public void update(RestaurantDTO restaurantDTO, Long id) {
//
//    }
//
//    @Override
//    public IDObject add(RestaurantDTO restaurantDTO) {
//        if (!restaurantRepository.existsByAddress(restaurantDTO.getAddress())) {
//            Restaurant newRestaurant = toRestaurantFromDTO(restaurantDTO);
//            restaurantRepository.save(newRestaurant);
//            return new IDObject(newRestaurant.getId());
//        } else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with that address already exist in database");
//        }
//    }

    public Restaurant toRestaurantFromDTO(RestaurantDTO restaurantDTO) {
        return modelMapper.map(restaurantDTO, Restaurant.class);
    }

    public RestaurantDTO toRestaurantDTO(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }

    public Menu toMenuFromDTO(MenuDTO menuDTO) {
        return modelMapper.map(menuDTO, Menu.class);
    }

}
