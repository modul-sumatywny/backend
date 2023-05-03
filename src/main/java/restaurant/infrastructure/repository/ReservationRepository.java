package restaurant.infrastructure.repository;

import restaurant.application.dto.reservationDto.ReservationDTO;
import restaurant.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
}
