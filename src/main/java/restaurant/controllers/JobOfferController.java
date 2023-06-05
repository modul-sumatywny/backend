package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.model.JobOffer;
import restaurant.model.dto.JobOfferDTO;
import restaurant.model.dto.JobOfferPostDTO;
import restaurant.model.mapper.JobOfferMapper;
import restaurant.service.JobOfferService;

@RestController
@RequestMapping("/jobOffers")
public class JobOfferController extends CrudController<Long, JobOffer, JobOfferDTO, JobOfferPostDTO> {
    private final JobOfferService jobOfferService;
    private final JobOfferMapper jobOfferMapper = Mappers.getMapper(JobOfferMapper.class);

    public JobOfferController(JobOfferService jobOfferService) {
        super(Mappers.getMapper(JobOfferMapper.class), jobOfferService);
        this.jobOfferService = jobOfferService;
    }
}
