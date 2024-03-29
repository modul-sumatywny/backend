package restaurant.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DishPostDto {

    @NotBlank(message = "Name of dish cannot be blank")
    private String name;

    @NotNull(message = "Price of dish cannot be null")
    private Integer price;

    @NotBlank(message = "Category of dish cannot be blank")
    private String category;

    @NotNull
    private Map<Long, Integer> productsWithQuantities = new HashMap<>();
}
