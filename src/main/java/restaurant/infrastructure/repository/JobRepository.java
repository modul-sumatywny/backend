package restaurant.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.domain.model.Job;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Long> {
    boolean existsByName(String name);

    Optional<Job> findByName(String name);
}
