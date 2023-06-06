package restaurant.model.mapper;

import org.mapstruct.Mapper;
import restaurant.model.JobOffer;
import restaurant.model.Restaurant;
import restaurant.model.dto.JobOfferDto;
import restaurant.model.dto.JobOfferPostDto;

@Mapper
public interface JobOfferMapper extends MapperBase<JobOffer, JobOfferDto, JobOfferPostDto> {

    @Override
    default JobOffer postDtoToEntity(JobOfferPostDto postDTO) {
        return JobOffer.builder()
                .name(postDTO.getName())
                .description(postDTO.getDescription())
                .restaurant(Restaurant.builder().id(postDTO.getRestaurantId()).build())
                .build();
    }


}
