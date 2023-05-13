package restaurant.application.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import restaurant.application.dto.jobOfferDto.JobOfferDTO;
import restaurant.application.dto.jobOfferDto.JobOfferWithIdDTO;
import restaurant.domain.facadeImpl.JobOfferFacadeImpl;
import restaurant.domain.model.IDObject;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobOffers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class JobOfferController {
    private final JobOfferFacadeImpl jobOfferFacadeImpl;

    @Autowired
    public JobOfferController(JobOfferFacadeImpl jobOfferFacadeImpl) { this.jobOfferFacadeImpl = jobOfferFacadeImpl; }

    @GetMapping("/{jobOfferId}")
    public Optional<JobOfferDTO> getJobOffer(@PathVariable Long jobOfferId) {
        return jobOfferFacadeImpl.get(jobOfferId);
    }

    @PostMapping("/add")
    public IDObject addJobOffer(@Valid @RequestBody JobOfferDTO jobOfferDTO) {
        return jobOfferFacadeImpl.add(jobOfferDTO);
    }

    @DeleteMapping("/delete/{jobOfferId}")
    public void deleteJobOffer(@PathVariable Long jobOfferId) { jobOfferFacadeImpl.delete(jobOfferId); }

    @PutMapping("/update/{jobOfferId}")
    public void updateJobOffer(@Valid @RequestBody JobOfferDTO jobOfferDTO, @PathVariable Long jobOfferId) {
        jobOfferFacadeImpl.update(jobOfferDTO, jobOfferId);
    }
    @PutMapping("/changeStatus/{jobOfferId}")
    public void changeStatus(@PathVariable Long jobOfferId) {
        jobOfferFacadeImpl.changeStatus(jobOfferId);
    }
    @GetMapping("/allJobOffers")
    public List<JobOfferWithIdDTO> getAllJobOffers() {
        return jobOfferFacadeImpl.getAll();
    }
}
