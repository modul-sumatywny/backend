package restaurant.application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import restaurant.application.dto.formDto.FormDTO;
import restaurant.application.dto.formDto.FormWithIdDTO;
import restaurant.domain.facadeImpl.FormFacadeImpl;
import restaurant.domain.model.IDObject;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/forms")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FormController {
    private final FormFacadeImpl formFacadeImpl;

    @Autowired
    public FormController(FormFacadeImpl formFacadeImpl) { this.formFacadeImpl = formFacadeImpl; }

    @GetMapping("/{formId}")
    public Optional<FormDTO> getForm(@PathVariable Long formId) {
        return formFacadeImpl.get(formId);
    }

    @PostMapping("/add")
    public IDObject addForm(@Valid @RequestBody FormDTO formDTO) {
        return formFacadeImpl.add(formDTO);
    }

    @DeleteMapping("/delete/{formId}")
    public void deleteForm(@PathVariable Long formId) {formFacadeImpl.delete(formId); }

    @GetMapping("/allForms")
    public List<FormWithIdDTO> getAllForms() {
        return formFacadeImpl.getAll();
    }
}
