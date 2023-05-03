package ms.restaurant.infrastructure.repository;

import ms.restaurant.application.dto.reservationDto.ReservationDTO;
import ms.restaurant.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
}
