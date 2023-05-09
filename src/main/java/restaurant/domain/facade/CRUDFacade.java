package restaurant.domain.facade;

import restaurant.domain.model.IDObject;

import java.util.List;
import java.util.Optional;

public interface CRUDFacade<T, U> {
    Optional<T> get(Long id);;
    void delete(Long id);
    void update(T t, Long id);

    List<U> getAll();

    IDObject add(T t);
}
