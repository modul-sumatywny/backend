package ms.restaurant.application.dto.dishDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.restaurant.domain.model.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDishDTO {
    @NotBlank(message = "Name of dish cannot be blank")
    private String name;

    @NotNull(message = "Price of dish cannot be null")
    private Integer price;
}
