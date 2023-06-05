package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.JobOffer;
import restaurant.model.Product;
import restaurant.model.dto.JobOfferDTO;
import restaurant.model.dto.JobOfferPostDTO;

@Mapper
public interface JobOfferMapper extends MapperBase<JobOffer, JobOfferDTO, JobOfferPostDTO> {

    @Override
    default JobOffer postDtoToEntity(JobOfferPostDTO postDTO) {
        return JobOffer.builder()
                .name(postDTO.getName())
                .description(postDTO.getDescription())
                .build();
    }
}
