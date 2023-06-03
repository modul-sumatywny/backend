package restaurant.service;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import restaurant.model.Reservation;
import restaurant.model.Table;
import restaurant.repository.TableRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableService extends CrudService<Long, Table> {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        super(tableRepository, Table.class);

        this.tableRepository = tableRepository;
    }

    public List<Table> findTablesByRestaurantId(Long restaurantId) {
        return tableRepository.findTablesByRestaurantId(restaurantId);
    }


}
