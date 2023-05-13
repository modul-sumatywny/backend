package restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.application.dto.formDto.FormDTO;
import restaurant.application.dto.formDto.FormWithIdDTO;
import restaurant.domain.facade.CRUDFacade;
import restaurant.domain.model.Form;
import restaurant.domain.model.IDObject;
import restaurant.domain.model.JobOffer;
import restaurant.infrastructure.repository.FormRepository;
import restaurant.infrastructure.repository.JobOfferRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FormFacadeImpl implements CRUDFacade<FormDTO, FormWithIdDTO> {

    private final FormRepository formRepository;
    private final JobOfferRepository jobOfferRepository;
    private final ModelMapper modelMapper;

    public Form toFormFromDTO(FormDTO formDTO) {
        return modelMapper.map(formDTO, Form.class);
    }
    public FormDTO toFormDTO(Form form) {
        return modelMapper.map(form, FormDTO.class);
    }

    public FormWithIdDTO toFormWithIdDTO(Form form) {
        return modelMapper.map(form, FormWithIdDTO.class);
    }

    @Override
    public Optional<FormDTO> get(Long id) {
        Form form = formRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
        return Optional.ofNullable(toFormDTO(form));
    }

    @Override
    public void delete(Long id) {
        if (formRepository.existsById(id)) {
            formRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Form with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(FormDTO formDTO, Long id) {
    }

    @Override
    public List<FormWithIdDTO> getAll() {
        List<FormWithIdDTO> forms = new ArrayList<>();
        for (Form form : formRepository.findAll()) {
            forms.add(toFormWithIdDTO(form));
        }
        return forms;
    }

    @Override
    public IDObject add(FormDTO formDTO) {
        JobOffer existingJobOffer = jobOfferRepository.findById(formDTO.getJobOfferId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job offer with this ID doesn't exist in database"));
        Form newForm = new Form();
        newForm.setJobOffer(existingJobOffer);
        newForm.setFirstname(formDTO.getFirstname());
        newForm.setLastname(formDTO.getLastname());
        newForm.setEmail(formDTO.getEmail());
        newForm.setPhoneNumber(formDTO.getPhoneNumber());
        formRepository.save(newForm);
        return new IDObject(newForm.getId());
    }
}
