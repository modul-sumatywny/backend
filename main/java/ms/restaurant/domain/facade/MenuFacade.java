package ms.restaurant.domain.facade;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface MenuFacade {
    ResponseEntity<Map<String, String>> getMenu(Long id);
}
