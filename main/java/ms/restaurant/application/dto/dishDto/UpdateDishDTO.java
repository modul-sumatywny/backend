package ms.restaurant.application.dto.dishDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishDTO {
    @NotBlank(message = "Name of dish cannot be blank")
    private String name;

    @NotNull(message = " of dish cannot be null")
    private Integer price;

    @NotBlank(message = "Category of dish cannot be blank")
    private String category;
}
