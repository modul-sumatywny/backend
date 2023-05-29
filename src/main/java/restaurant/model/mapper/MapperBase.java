package restaurant.model.mapper;

import restaurant.model.ModelEntity;

public interface MapperBase<TEntity extends ModelEntity<?>, TDto, TPostDto> {
    TDto entityToDto(TEntity entity);
    TEntity dtoToEntity(TDto dto);
    TDto postDtoToDto(TPostDto postDto);
    TEntity postDtoToEntity(TPostDto postDto);
}
