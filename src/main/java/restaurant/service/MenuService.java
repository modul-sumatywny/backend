package restaurant.service;

import restaurant.model.Menu;
import restaurant.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MenuService extends CrudService<Long, Menu> {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        super(menuRepository, Menu.class);

        this.menuRepository = menuRepository;
    }
}
