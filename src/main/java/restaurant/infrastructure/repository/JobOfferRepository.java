package restaurant.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.domain.model.Job;
import restaurant.domain.model.JobOffer;
import restaurant.domain.model.Restaurant;

import java.util.Optional;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long> {
    boolean existsByJobAndRestaurant(Job job, Restaurant restaurant);
    Optional<JobOffer> findByJobAndRestaurant(Job job, Restaurant restaurant);

}
