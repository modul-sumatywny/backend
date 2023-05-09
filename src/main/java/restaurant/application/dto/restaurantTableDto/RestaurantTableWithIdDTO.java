package restaurant.application.dto.restaurantTableDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class RestaurantTableWithIdDTO {
    private Long id;

    @NotNull
    private Integer tableNumber;

    @NotNull
    private Integer numberOfSeats;
}
