package restaurant.service;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.exception.EntityNotFoundException;
import restaurant.model.ModelEntity;
import restaurant.repository.RepositoryBase;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public abstract class CrudService<ID, TEntity extends ModelEntity<ID>> {

    private final RepositoryBase<TEntity, ID> entityRepository;
    private final Class<TEntity> type;

    protected CrudService(RepositoryBase<TEntity, ID> entityRepository, Class<TEntity> type) {
        this.entityRepository = entityRepository;
        this.type = type;
    }

    public List<TEntity> getAll() {
        return entityRepository
                .findAll();
    }

    public TEntity getById(ID id) {
        return entityRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(type, "id", id));
    }

    public List<TEntity> getByIds(Collection<ID> ids) {
        return entityRepository
                .findAllById(ids);
    }

    public TEntity create(TEntity entity) {
        return entityRepository
                .save(entity);
    }

    public TEntity update(ID id, TEntity entity) {
        entity.setId(id);
        return entityRepository
                .save(entity);
    }

    public TEntity update(ID id, Consumer<TEntity> modExpr) {
        TEntity entity = getById(id);
        modExpr.accept(entity);

        return entityRepository
                .save(entity);
    }

    public TEntity delete(ID id) {
        TEntity entity = getById(id);
        entityRepository.delete(entity);

        return entity;
    }
}
