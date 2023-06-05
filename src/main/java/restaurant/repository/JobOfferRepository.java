package restaurant.repository;

import org.springframework.stereotype.Repository;
import restaurant.model.Dish;
import restaurant.model.JobOffer;

@Repository
public interface JobOfferRepository extends RepositoryBase<JobOffer, Long>{
}
