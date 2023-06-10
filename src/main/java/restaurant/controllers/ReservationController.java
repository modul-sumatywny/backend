package restaurant.controllers;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Future;
import org.mapstruct.factory.Mappers;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<List<ReservationDto>> getAllTEntities() {
        return super.getAllTEntities();
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<ReservationDto> getTEntityById(Long aLong) {
        return super.getTEntityById(aLong);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_ADMIN'})")
    public ResponseEntity<ReservationDto> createTEntity(ReservationPostDto entityPostDto) {
        return super.createTEntity(entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_ADMIN'})")
    public ResponseEntity<ReservationDto> updateTEntity(Long aLong, ReservationPostDto entityPostDto) {
        return super.updateTEntity(aLong, entityPostDto);
    }

    @Override
    @PreAuthorize("hasAnyAuthority({'SCOPE_MANAGER'})")
    public ResponseEntity<ReservationDto> deleteTEntity(Long aLong) {
        return super.deleteTEntity(aLong);
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
            return ok(reservationService.getTimes(restaurantId, numberOfGuests, reservationTime));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{restaurantId}")
    @PreAuthorize("hasAnyAuthority({'SCOPE_EMPLOYEE'})")
    public ResponseEntity<?> getReservationsForRestaurant(
            @PathVariable Long restaurantId,
            @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime reservationTime) {
        try {
            List<ReservationDto> reservations;
            if(reservationTime==null) {
                reservations = reservationService.getAll().stream()
                        .filter(reservation -> reservation.getTable().getRestaurant().getId().equals(restaurantId))
                        .map(mapper::entityToDto)
                        .collect(Collectors.toList());
            } else {
                LocalDateTime startDateTime = reservationTime.with(LocalTime.MIN); // Ustawienie godziny na 00:00
                LocalDateTime endDateTime = reservationTime.plusDays(1).with(LocalTime.MIN); // Ustawienie godziny na 00:00 kolejnego dnia

                reservations = reservationService.findByReservationDateTimeBeetwen(startDateTime, endDateTime)
                        .stream()
                        .filter(reservation -> reservation.getTable().getRestaurant().getId().equals(restaurantId))
                        .map(mapper::entityToDto)
                        .collect(Collectors.toList());
            }
            return ok(reservations);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/reserve-table/{restaurantId}")
    @PreAuthorize("hasAnyAuthority({'SCOPE_CLIENT'})")
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
            return ok(mapper.entityToDto(reservationService.reserveTable(restaurantId, accountId, numberOfGuests, reservationTime)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

