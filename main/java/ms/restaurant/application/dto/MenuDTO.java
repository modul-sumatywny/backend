package ms.restaurant.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ms.restaurant.domain.model.Dish;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    @NotBlank(message = "Name of menu cannot be blank")
    private String name;

    @NotNull
    @Size(min = 1, message = "There must be at least 1 dish in menu")
    private List<Dish> dishes;


}
