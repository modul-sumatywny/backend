package restaurant.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.model.ModelEntity;
import restaurant.model.mapper.MapperBase;
import restaurant.service.CrudService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
public abstract class CrudController<ID, TEntity extends ModelEntity<ID>, TDto, TPostDto> {

    protected final MapperBase<TEntity, TDto, TPostDto> mapper;
    protected final CrudService<ID, TEntity> entityService;

    @GetMapping(
            value = "",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<TDto>> getAllTEntities() {
        try {
            List<TEntity> entities = entityService.getAll();

            return ok(entities.stream()
                    .map(mapper::entityToDto)
                    .toList()
            );
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @GetMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TDto> getTEntityById(@PathVariable ID id) {
        try {
            TEntity entity = entityService.getById(id);
            return ok(mapper.entityToDto(entity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TDto> createTEntity(@RequestBody TPostDto entityPostDto) {
        try {
            TEntity entity = mapper.postDtoToEntity(entityPostDto);
            TEntity createdTEntity = entityService.create(entity);

            return ok(mapper.entityToDto(createdTEntity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @PutMapping(
            value = "{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TDto> updateTEntity(@PathVariable ID id, @RequestBody TPostDto entityPostDto) {
        try {
            TEntity entity = mapper.postDtoToEntity(entityPostDto);
            TEntity updatedTEntity = entityService.update(id, entity);

            return ok(mapper.entityToDto(updatedTEntity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Transactional
    @DeleteMapping(
            value = "{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<TDto> deleteTEntity(@PathVariable ID id) {
        try {
            TEntity deletedTEntity = entityService.delete(id);
            return ok(mapper.entityToDto(deletedTEntity));
        } catch (Exception e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }
}
