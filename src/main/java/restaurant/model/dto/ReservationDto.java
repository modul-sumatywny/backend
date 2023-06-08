package restaurant.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long id;

    private Long accountId;

    @NotNull(message = "Reservation time cannot be null")
    @Future(message = "Reservation time must be in future time")
    private String reservationDateTime;

    private Long tableId;
}
