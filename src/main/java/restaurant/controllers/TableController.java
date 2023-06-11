package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Dish;
import restaurant.model.Table;
import restaurant.model.dto.MenuDto;
import restaurant.model.dto.TableDto;
import restaurant.model.dto.TablePostDto;
import restaurant.model.mapper.TableMapper;
import restaurant.service.TableService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/tables")
public class TableController extends CrudController<Long, Table, TableDto, TablePostDto> {

    private final TableService tableService;

    public TableController(TableService tableService) {
        super(Mappers.getMapper(TableMapper.class), tableService);

        this.tableService = tableService;
    }

    @Override
  //  @PreAuthorize("hasAnyAuthority({'SCOPE_ADMIN','SCOPE_MANAGER'})")
    public ResponseEntity<List<TableDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    public ResponseEntity<TableDto> getTEntityById(Long aLong) {
        return super.getTEntityById(aLong);
    }

    @Override
    public ResponseEntity<TableDto> createTEntity(TablePostDto entityPostDto) {
        return super.createTEntity(entityPostDto);
    }

    @Override
    public ResponseEntity<TableDto> updateTEntity(Long aLong, TablePostDto entityPostDto) {
        return super.updateTEntity(aLong, entityPostDto);
    }

    @Override
    @PreAuthorize("hasAuthority({'ADMIN','MANAGER'})")
    public ResponseEntity<TableDto> deleteTEntity(Long aLong) {
        return super.deleteTEntity(aLong);
    }
}
