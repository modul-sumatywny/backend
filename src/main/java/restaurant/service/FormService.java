package restaurant.service;

import org.springframework.stereotype.Repository;
import restaurant.model.Dish;
import restaurant.model.Form;
import restaurant.repository.DishRepository;
import restaurant.repository.FormRepository;

@Repository
public class FormService extends CrudService<Long, Form> {
    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) {
        super(formRepository, Form.class);

        this.formRepository = formRepository;
    }
}
