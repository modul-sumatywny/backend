package restaurant.model.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TablePostDto {

    @NotNull(message = "Table number to restaurant cannot be null")
    private Integer tableNumber;

    @Min(value = 1, message = "Address of restaurant cannot be blank")
    private Integer numberOfSeats;

    @NotNull(message = "Restaurant Id cannot be null!")
    private Long restaurantId;
}