package restaurant.controllers;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Future;
import org.mapstruct.factory.Mappers;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Reservation;
import restaurant.model.Table;
import restaurant.model.dto.ReservationDto;
import restaurant.model.dto.ReservationPostDto;
import restaurant.model.mapper.ReservationMapper;
import restaurant.service.ReservationService;
import restaurant.service.TableService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/reservations")
public class ReservationController extends CrudController<Long, Reservation, ReservationDto, ReservationPostDto> {

    private final ReservationService reservationService;
    private final TableService tableService;

    public ReservationController(ReservationService reservationService, TableService tableService) {
        super(Mappers.getMapper(ReservationMapper.class), reservationService);
        this.reservationService = reservationService;
        this.tableService = tableService;
    }

    @GetMapping("/reservation-options/{restaurantId}")
    public ResponseEntity<?> getReservationOptions(
            @PathVariable Long restaurantId,
            @RequestParam int numberOfGuests,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime reservationTime
    ) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            if (reservationTime.isBefore(currentTime)) {
                throw new IllegalArgumentException("Reservation time must be in the future");
            }
            return ok(reservationService.getTimes(restaurantId,numberOfGuests,reservationTime));
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/reserve-table/{restaurantId}")
    public ResponseEntity<?> reserveTable(
            @PathVariable Long restaurantId,
            @RequestParam Long accountId,
            @RequestParam int numberOfGuests,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime reservationTime
    ) {
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            if (reservationTime.isBefore(currentTime)) {
                throw new IllegalArgumentException("Reservation time must be in the future");
            }
            return ok(reservationService.reserveTable(restaurantId,accountId,numberOfGuests,reservationTime));
        }catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

