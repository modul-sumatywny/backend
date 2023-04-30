package ms.restaurant.application.dto;

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
public class DishDTO {
    @NotBlank(message = "Name of dish cannot be blank")
    private String name;

    @NotNull(message = "Price of dish cannot be null")
    private Integer price;

    @NotNull
    @Size(min = 1, message = "There must be at least 1 ingredient for dish")
    private List<Product> products;
}
