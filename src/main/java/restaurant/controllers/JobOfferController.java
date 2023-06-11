package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import restaurant.model.JobOffer;
import restaurant.model.dto.JobOfferDto;
import restaurant.model.dto.JobOfferPostDto;
import restaurant.model.mapper.JobOfferMapper;
import restaurant.service.JobOfferService;

import java.util.List;

@RestController
@RequestMapping("/jobOffers")
public class JobOfferController extends CrudController<Long, JobOffer, JobOfferDto, JobOfferPostDto> {
    private final JobOfferService jobOfferService;
    private final JobOfferMapper jobOfferMapper = Mappers.getMapper(JobOfferMapper.class);

    public JobOfferController(JobOfferService jobOfferService) {
        super(Mappers.getMapper(JobOfferMapper.class), jobOfferService);
        this.jobOfferService = jobOfferService;
    }

    @Override
    public ResponseEntity<List<JobOfferDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    public ResponseEntity<JobOfferDto> getTEntityById(@PathVariable Long id) {
        return super.getTEntityById(id);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<JobOfferDto> createTEntity(@RequestBody JobOfferPostDto entityPostDto) {
        return super.createTEntity(entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<JobOfferDto> updateTEntity(@PathVariable Long id,@RequestBody JobOfferPostDto entityPostDto) {
        return super.updateTEntity(id, entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<JobOfferDto> deleteTEntity(@PathVariable Long id) {
        return super.deleteTEntity(id);
    }
}
