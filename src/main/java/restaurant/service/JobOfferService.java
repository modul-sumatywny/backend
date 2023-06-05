package restaurant.service;

import org.springframework.stereotype.Service;
import restaurant.model.JobOffer;
import restaurant.model.Product;
import restaurant.repository.JobOfferRepository;
import restaurant.repository.ProductRepository;

@Service
public class JobOfferService extends CrudService<Long, JobOffer> {
    private final JobOfferRepository jobOfferRepository;

    public JobOfferService(JobOfferRepository jobOfferRepository) {
        super(jobOfferRepository, JobOffer.class);

        this.jobOfferRepository = jobOfferRepository;
    }
}
