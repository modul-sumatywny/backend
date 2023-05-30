package restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.model.Dish;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPostDto {

    @NotBlank(message = "Name of product cannot be blank")
    private String name;

    @NotBlank(message = "EAN number of product cannot be blank")
    private String ean;
}
