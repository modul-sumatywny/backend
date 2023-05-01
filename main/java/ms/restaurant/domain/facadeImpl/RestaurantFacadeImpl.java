package ms.restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.RestaurantDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.model.IDObject;
import ms.restaurant.domain.model.Restaurant;
import ms.restaurant.infrastructure.repository.RestaurantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestaurantFacadeImpl implements CRUDFacade<RestaurantDTO> {
    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    public Restaurant toRestaurantFromDTO(RestaurantDTO restaurantDTO) {
        return modelMapper.map(restaurantDTO, Restaurant.class);
    }

    public RestaurantDTO toRestaurantDTO(Optional<Restaurant> restaurant) {
        return modelMapper.map(restaurant, RestaurantDTO.class);
    }


    @Override
    public Optional<RestaurantDTO> get(Long id) {
        if (restaurantRepository.existsById(id)) {
            Optional<Restaurant> restaurant = restaurantRepository.findById(id);
            if (restaurant.isPresent()) {
                return Optional.ofNullable(toRestaurantDTO(restaurant));
            } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database");
        }
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(RestaurantDTO restaurantDTO, Long id) {

    }

    @Override
    public IDObject add(RestaurantDTO restaurantDTO) {
        if (!restaurantRepository.existsByAddress(restaurantDTO.getAddress())) {
            Restaurant newRestaurant = toRestaurantFromDTO(restaurantDTO);
            restaurantRepository.save(newRestaurant);
            return new IDObject(newRestaurant.getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with that address already exist in database");
        }
    }
}
