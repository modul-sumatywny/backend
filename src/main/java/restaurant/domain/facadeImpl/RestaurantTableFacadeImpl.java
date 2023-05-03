package restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import restaurant.application.dto.restaurantTableDto.RestaurantTableDTO;
import restaurant.domain.facade.CRUDFacade;
import restaurant.domain.model.IDObject;
import restaurant.domain.model.RestaurantTable;
import restaurant.infrastructure.repository.RestaurantTableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RestaurantTableFacadeImpl implements CRUDFacade<RestaurantTableDTO> {
    private final RestaurantTableRepository restaurantTableRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<RestaurantTableDTO> get(Long id) {
        RestaurantTable restaurantTable = restaurantTableRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Table not found"));
        return Optional.ofNullable(toTableDTO(restaurantTable));
    }

    @Override
    public void delete(Long id) {
        if (restaurantTableRepository.existsById(id)) {
            restaurantTableRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(RestaurantTableDTO restaurantTableDTO, Long id) {
        RestaurantTable existingRestaurantTable = restaurantTableRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Table with this ID doesnt exists in database!"));

        if (restaurantTableRepository.existsById(id)) {
            existingRestaurantTable.setId(id);
            existingRestaurantTable.setTableNumber(restaurantTableDTO.getTableNumber());
            existingRestaurantTable.setNumberOfSeats(restaurantTableDTO.getNumberOfSeats());
            restaurantTableRepository.save(existingRestaurantTable);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Table just got updated");
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Table with this ID doesn't exist in database");
        }
    }


    //musi tu byc bo implementuje interfejs CRUDFacade
    @Override
    public IDObject add(RestaurantTableDTO restaurantTableDTO) {
        return null;
    }

    public RestaurantTableDTO toTableDTO(restaurant.domain.model.RestaurantTable restaurantTable) {
        return modelMapper.map(restaurantTable, RestaurantTableDTO.class);
    }
}
