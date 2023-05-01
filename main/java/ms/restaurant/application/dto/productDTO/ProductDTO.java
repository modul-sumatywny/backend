package ms.restaurant.application.dto.productDTO;


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
public class ProductDTO {
    @NotBlank(message = "Name of product cannot be blank")
    private String name;

    @NotBlank(message = "EAN number of product cannot be blank")
    private String ean;

//    @NotNull
//    @Size(min = 1, message = "There must be at least 1 dish connected with this product")
//    private List<Dish> dishes;
}

