package restaurant.application.dto.dishDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.domain.model.Product;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishDTO {
    @NotBlank(message = "Name of dish cannot be blank")
    private String name;

    @NotNull(message = "Price of dish cannot be null")
    private Integer price;

    @NotBlank(message = "Category of dish cannot be blank")
    private String category;
}
