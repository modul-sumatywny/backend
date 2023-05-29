package restaurant.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<Long> productsIds = new ArrayList<>();

    @NotNull
    private List<Long> menusIds = new ArrayList<>();
}
