package restaurant.application.controllers;

import jakarta.validation.Valid;
import restaurant.application.dto.reservationDto.ReservationDTO;
import restaurant.application.dto.reservationDto.ReservationWithIdDTO;
import restaurant.application.dto.restaurantTableDto.RestaurantTableWithIdDTO;
import restaurant.domain.facadeImpl.ReservationFacadeImpl;
import restaurant.domain.model.IDObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservation")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ReservationController {
    private final ReservationFacadeImpl reservationFacadeImpl;

    @Autowired
    public ReservationController(ReservationFacadeImpl reservationFacadeImpl) {
        this.reservationFacadeImpl = reservationFacadeImpl;
    }

    @GetMapping("/get/{reservationId}")
    public Optional<ReservationDTO> getReservation(@PathVariable Long reservationId) {
        return reservationFacadeImpl.get(reservationId);
    }

    @DeleteMapping("/delete/{reservationId}")
    public void deleteOrder(@PathVariable Long reservationId) {
        reservationFacadeImpl.delete(reservationId);
    }

    @PostMapping("/add")
    public IDObject addReservation(@Valid @RequestBody ReservationDTO reservationDTO) {
        return reservationFacadeImpl.add(reservationDTO);
    }

    @GetMapping("/allReservations")
    public List<ReservationWithIdDTO> getAllReservations() {
        return reservationFacadeImpl.getAll();
    }

}
