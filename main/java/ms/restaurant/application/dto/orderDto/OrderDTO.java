package ms.restaurant.application.dto.orderDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    @NotNull
    @Size(min = 1, message = "Order must contain at least 1 dish")
    @Valid
    private List<Long> dishesIDs;

    @NotNull(message = "Restaurant ID must not be null")
    private Long restaurantId;
}
