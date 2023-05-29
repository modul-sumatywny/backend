package restaurant.service;

import restaurant.model.Reservation;
import restaurant.repository.ReservationRepository;
import org.springframework.stereotype.Service;

@Service
public class ReservationService extends CrudService<Long, Reservation> {
    private final ReservationRepository reservationRepository;

    public ReservationService(ReservationRepository reservationRepository) {
        super(reservationRepository, Reservation.class);

        this.reservationRepository = reservationRepository;
    }
}
