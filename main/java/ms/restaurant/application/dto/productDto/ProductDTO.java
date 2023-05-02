package ms.restaurant.application.dto.productDto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Name of product cannot be blank")
    private String name;

    @NotBlank(message = "EAN number of product cannot be blank")
    private String ean;
}

