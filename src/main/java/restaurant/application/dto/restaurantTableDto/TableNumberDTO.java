package restaurant.application.dto.restaurantTableDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableNumberDTO {
    @NotNull
    private Integer tableNumber;

}
