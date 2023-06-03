package restaurant.repository;

import org.springframework.stereotype.Repository;
import restaurant.model.Reservation;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends RepositoryBase<Reservation, Long> {
    List<Reservation> findByTableIdAndReservationDateTimeBetween(Long table_id, LocalDateTime reservationDateTime, LocalDateTime reservationDateTime2);
}
