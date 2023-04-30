package ms.restaurant.domain.facade;

import ms.restaurant.domain.model.IDObject;

import java.util.Optional;

public interface CRUDFacade<T> {
    Optional<T> get(Long id);;
    void delete(Long id);
    void update(T t, Long id);
    IDObject add(T t);
}
