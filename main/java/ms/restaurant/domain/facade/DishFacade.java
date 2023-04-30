package ms.restaurant.domain.facade;

import ms.restaurant.application.dto.DishDTO;
import ms.restaurant.domain.model.IDObject;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Optional;

public interface DishFacade {
    ResponseEntity<Map<String, String>> getDish(Long id);
//    void deleteDish(Long id);
//    void updateDish(DishDTO dishDTO, Long id);
//    IDObject addDish(DishDTO dishDTO);
}
