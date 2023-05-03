package ms.restaurant.application.dto.reservationDto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    @NotBlank(message = "Name of the owner of the reservation cannot be blank")
    private String name;

    @NotNull(message = "Reservation time cannot be null")
    @Future(message = "Reservation time must be in future time")
    private LocalDateTime reservationDateTime;

    @NotNull(message = "Restaurant table ID must not be null")
    private Long restaurantTableId;
}
