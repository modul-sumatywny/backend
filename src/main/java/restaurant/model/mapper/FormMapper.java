package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.Dish;
import restaurant.model.Form;
import restaurant.model.JobOffer;
import restaurant.model.dto.FormDto;
import restaurant.model.dto.FormPostDto;

@Mapper
public interface FormMapper extends MapperBase<Form, FormDto, FormPostDto> {
    @Override
    default Form postDtoToEntity(FormPostDto formPostDto) {
        return Form.builder()
                .firstname(formPostDto.getFirstname())
                .email(formPostDto.getEmail())
                .lastname(formPostDto.getLastname())
                .phoneNumber(formPostDto.getPhoneNumber())
                .jobOffer(JobOffer.builder().id(formPostDto.getJobOfferId()).build())
                .build();
    }

    @Override
    default FormDto entityToDto(Form entity) {
        return FormDto.builder()
                .jobOfferId(entity.getJobOffer().getId())
                .firstname(entity.getFirstname())
                .lastname(entity.getLastname())
                .email(entity.getEmail())
                .id(entity.getId())
                .phoneNumber(entity.getPhoneNumber())
                .build();
    }
}
