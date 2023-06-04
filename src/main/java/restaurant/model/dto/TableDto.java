package restaurant.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TableDto {

    private Long id;

    @NotNull(message = "Table number to restaurant cannot be null")
    private Integer tableNumber;

    @Min(value = 1, message = "Address of restaurant cannot be blank")
    private Integer numberOfSeats;

    private Long restaurantId;
}