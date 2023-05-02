package ms.restaurant.application.dto.dishDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishNameDTO {
    @NotBlank(message = "Name of dish cannot be blank")
    private String name;
}
