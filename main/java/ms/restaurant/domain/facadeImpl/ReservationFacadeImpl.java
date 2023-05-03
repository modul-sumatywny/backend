package ms.restaurant.domain.facadeImpl;

import lombok.RequiredArgsConstructor;
import ms.restaurant.application.dto.reservationDto.ReservationDTO;
import ms.restaurant.domain.facade.CRUDFacade;
import ms.restaurant.domain.model.*;
import ms.restaurant.infrastructure.repository.ReservationRepository;
import ms.restaurant.infrastructure.repository.RestaurantTableRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationFacadeImpl implements CRUDFacade<ReservationDTO> {
    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<ReservationDTO> get(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
        return Optional.ofNullable(toReservationDTO(reservation));
    }

    @Override
    public void delete(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation with this ID doesn't exist in database");
        }
    }

    @Override
    public void update(ReservationDTO reservationDTO, Long id) {

    }

    @Override
    public IDObject add(ReservationDTO reservationDTO) {
        Reservation newReservation = new Reservation();
        Optional<Reservation> existingReservation = reservationRepository.findById(reservationDTO.getRestaurantTableId());

        if (existingReservation.isPresent() && !existingReservation.get()
                .getReservationDateTime().isBefore(reservationDTO.getReservationDateTime().minusHours(2)))
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your reservation must be set for more 2 hours later than existing reservation");
        }

        RestaurantTable existingRestaurantTable = restaurantTableRepository.findById(reservationDTO.getRestaurantTableId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant table with this ID doesn't exist in database"));
        newReservation.setRestaurantTable(existingRestaurantTable);
        newReservation.setName(reservationDTO.getName());
        newReservation.setReservationDateTime(reservationDTO.getReservationDateTime());

        reservationRepository.save(newReservation);

        return new IDObject(newReservation.getId());
    }

    public ReservationDTO toReservationDTO(Reservation reservation) {
        return modelMapper.map(reservation, ReservationDTO.class);
    }
    public Reservation toReservationFromDTO(ReservationDTO reservationDTO) {
        return modelMapper.map(reservationDTO, Reservation.class);
    }
}
