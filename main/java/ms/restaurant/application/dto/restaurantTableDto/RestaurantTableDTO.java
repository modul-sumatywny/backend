package ms.restaurant.application.dto.restaurantTableDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantTableDTO {
    @NotNull
    private Integer tableNumber;

    @NotNull
    private Integer numberOfSeats;
}
