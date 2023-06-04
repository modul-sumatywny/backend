package restaurant.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.model.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPostDto {

    @NotNull(message = "First name must not be null")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    private String lastName;
    @NotNull(message = "Phone number must not be null")
    private String phoneNumber;
    @NotNull(message = "Order time must not be null")
    private LocalDateTime orderTime;

    @NotNull(message = "Restaurant ID must not be null")
    private Long restaurantId;

    @NotNull(message = "Account ID must not be null")
    private Long accountId;

    @Size(min = 1, message = "Order must contain at least 1 dish")
    private List<Long> dishesIDs = new ArrayList<>();
}
