package ms.restaurant.application.controllers;

import jakarta.validation.Valid;
import ms.restaurant.application.dto.reservationDto.ReservationDTO;
import ms.restaurant.domain.facadeImpl.ReservationFacadeImpl;
import ms.restaurant.domain.model.IDObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/reservation")
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

}
