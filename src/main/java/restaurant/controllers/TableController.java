package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
