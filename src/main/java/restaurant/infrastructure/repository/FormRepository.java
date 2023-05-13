package restaurant.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import restaurant.domain.model.Form;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {
}

