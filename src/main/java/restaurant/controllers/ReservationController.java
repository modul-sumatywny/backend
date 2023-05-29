package restaurant.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Reservation;
import restaurant.model.dto.ReservationDto;
import restaurant.model.dto.ReservationPostDto;
import restaurant.model.mapper.ReservationMapper;
import restaurant.service.ReservationService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends CrudController<Long, Reservation, ReservationDto, ReservationPostDto> {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        super(Mappers.getMapper(ReservationMapper.class), reservationService);

        this.reservationService = reservationService;
    }
}
