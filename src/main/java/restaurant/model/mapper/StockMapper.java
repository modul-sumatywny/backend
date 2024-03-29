package restaurant.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import restaurant.model.Stock;
import restaurant.model.dto.StockDto;
import restaurant.model.dto.StockPostDto;

@Mapper
public interface StockMapper extends MapperBase<Stock, StockDto, StockPostDto> {
    @Override
    default StockDto entityToDto(Stock entity) {
        return StockDto.builder()
                .stock(entity.getStock())
                .product(Mappers.getMapper(ProductMapper.class).entityToDto(entity.getProduct()))
                .restaurantId(entity.getRestaurant().getId())
                .id(entity.getId())
                .isEnabled(entity.getIsEnabled())
                .build();
    }
}
