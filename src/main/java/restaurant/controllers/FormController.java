package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.model.Form;
import restaurant.model.dto.FormDto;
import restaurant.model.dto.FormPostDto;
import restaurant.model.mapper.FormMapper;
import restaurant.model.mapper.JobOfferMapper;
import restaurant.service.FormService;
import restaurant.service.JobOfferService;

@RestController
@RequestMapping("/forms")
public class FormController extends CrudController<Long, Form, FormDto, FormPostDto> {
    private final FormService formService;
    private final FormMapper formMapper = Mappers.getMapper(FormMapper.class);

    public FormController(FormService formService) {
        super(Mappers.getMapper(FormMapper.class), formService);
        this.formService = formService;
    }
}
