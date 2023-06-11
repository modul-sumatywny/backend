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
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<List<TableDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<TableDto> getTEntityById(@PathVariable Long id) {
        return super.getTEntityById(id);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<TableDto> createTEntity(@RequestBody TablePostDto entityPostDto) {
        return super.createTEntity(entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<TableDto> updateTEntity(@PathVariable Long id,@RequestBody TablePostDto entityPostDto) {
        return super.updateTEntity(id, entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<TableDto> deleteTEntity(@PathVariable Long id) {
        return super.deleteTEntity(id);
    }
}
