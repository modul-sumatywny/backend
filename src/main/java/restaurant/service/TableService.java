package restaurant.service;

import restaurant.model.Table;
import restaurant.repository.TableRepository;
import org.springframework.stereotype.Service;

@Service
public class TableService extends CrudService<Long, Table> {
    private final TableRepository tableRepository;

    public TableService(TableRepository tableRepository) {
        super(tableRepository, Table.class);

        this.tableRepository = tableRepository;
    }
}
