package restaurant.domain.facade;

import restaurant.domain.model.IDObject;

import java.util.Optional;

public interface CRUDFacade<T> {
    Optional<T> get(Long id);;
    void delete(Long id);
    void update(T t, Long id);
    IDObject add(T t);
}
