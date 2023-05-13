package restaurant.domain.facadeImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import restaurant.application.dto.jobOfferDto.JobOfferDTO;
import restaurant.application.dto.jobOfferDto.JobOfferWithIdDTO;
import restaurant.domain.facade.CRUDFacade;
import restaurant.domain.model.IDObject;
import restaurant.domain.model.Job;
import restaurant.domain.model.JobOffer;
import restaurant.domain.model.Restaurant;
import restaurant.infrastructure.repository.JobOfferRepository;
import restaurant.infrastructure.repository.JobRepository;
import restaurant.infrastructure.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class JobOfferFacadeImpl  implements CRUDFacade<JobOfferDTO, JobOfferWithIdDTO>{
    private final JobOfferRepository jobOfferRepository;
    private final RestaurantRepository restaurantRepository;
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<JobOfferDTO> get(Long id) {
        JobOffer jobOffer = jobOfferRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job offer not found"));
        return Optional.ofNullable(toJobOfferDTO(jobOffer));
    }

    @Override
    public void delete(Long id) {
        if (jobOfferRepository.existsById(id)) {
            jobOfferRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Job offer with this ID doesn't exist in database");
        }
    }

    @Override
    @Transactional
    public void update(JobOfferDTO jobOfferDTO, Long id) {
            JobOffer updatedJobOffer = jobOfferRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job offer with this ID doesn't exist in database"));
            Restaurant existingRestaurant = restaurantRepository.findById(jobOfferDTO.getRestaurantId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database"));
            Job existingJob = jobRepository.findById(jobOfferDTO.getJobId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with this ID doesn't exist in database"));
            updatedJobOffer.setRestaurant(existingRestaurant);
            updatedJobOffer.setJob(existingJob);
            updatedJobOffer.setPartTime(jobOfferDTO.getPartTime());
            updatedJobOffer.setSalary(jobOfferDTO.getSalary());
            if(!jobOfferRepository.existsByJobAndRestaurant(existingJob, existingRestaurant) || jobOfferRepository.findByJobAndRestaurant(existingJob, existingRestaurant).get().getId().equals(id)) {
                jobOfferRepository.save(updatedJobOffer);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job offer for this job and restaurant already exists in database!");
            }
    }

    @Override
    public IDObject add(JobOfferDTO jobOfferDTO) {
        Restaurant existingRestaurant = restaurantRepository.findById(jobOfferDTO.getRestaurantId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant with this ID doesn't exist in database"));
        Job existingJob = jobRepository.findById(jobOfferDTO.getJobId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job with this ID doesn't exist in database"));
        JobOffer newJobOffer = new JobOffer();
        newJobOffer.setRestaurant(existingRestaurant);
        newJobOffer.setJob(existingJob);
        newJobOffer.setOfferStatus(JobOffer.OfferStatus.ACTIVE);
        newJobOffer.setPartTime(jobOfferDTO.getPartTime());
        newJobOffer.setSalary(jobOfferDTO.getSalary());
        if(!jobOfferRepository.existsByJobAndRestaurant(existingJob, existingRestaurant)) {
            jobOfferRepository.save(newJobOffer);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Job offer for this job and restaurant already exists in database!");
        }
        return new IDObject(newJobOffer.getId());
    }


    public void changeStatus(Long jobOfferId) {
        JobOffer jobOffer = jobOfferRepository.findById(jobOfferId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job offer with this ID doesn't exist in database"));
        if(jobOffer.getOfferStatus() == JobOffer.OfferStatus.ACTIVE) {
            jobOffer.setOfferStatus(JobOffer.OfferStatus.INACTIVE);
        } else {
            jobOffer.setOfferStatus(JobOffer.OfferStatus.ACTIVE);
        }
        jobOfferRepository.save(jobOffer);
    }

    public JobOfferDTO toJobOfferDTO(JobOffer jobOffer) {
        return modelMapper.map(jobOffer, JobOfferDTO.class);
    }
    public JobOfferWithIdDTO toJobOfferWithIdDTO(JobOffer jobOffer) {
        return modelMapper.map(jobOffer, JobOfferWithIdDTO.class);
    }

    public JobOffer toJobOfferFromDTO(JobOfferDTO jobOfferDTO) {
        return modelMapper.map(jobOfferDTO, JobOffer.class);
    }

    @Override
    public List<JobOfferWithIdDTO> getAll() {
        List<JobOfferWithIdDTO> jobOffers = new ArrayList<>();
        for (JobOffer jobOffer : jobOfferRepository.findAll()) {
            jobOffers.add(toJobOfferWithIdDTO(jobOffer));
        }
        return jobOffers;
    }

}
