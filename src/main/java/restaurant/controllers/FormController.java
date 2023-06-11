package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.model.Form;
import restaurant.model.dto.FormDto;
import restaurant.model.dto.FormPostDto;
import restaurant.model.mapper.FormMapper;
import restaurant.model.mapper.JobOfferMapper;
import restaurant.service.FormService;
import restaurant.service.JobOfferService;

import java.util.List;

@RestController
@RequestMapping("/forms")
public class FormController extends CrudController<Long, Form, FormDto, FormPostDto> {
    private final FormService formService;
    private final FormMapper formMapper = Mappers.getMapper(FormMapper.class);

    public FormController(FormService formService) {
        super(Mappers.getMapper(FormMapper.class), formService);
        this.formService = formService;
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<List<FormDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<FormDto> getTEntityById(@PathVariable Long id) {
        return super.getTEntityById(id);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_CLIENT'})")
    public ResponseEntity<FormDto> createTEntity(@RequestBody FormPostDto entityPostDto) {
        return super.createTEntity(entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<FormDto> updateTEntity(@PathVariable Long id,@RequestBody FormPostDto entityPostDto) {
        return super.updateTEntity(id, entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<FormDto> deleteTEntity(@PathVariable Long id) {
        return super.deleteTEntity(id);
    }
}
