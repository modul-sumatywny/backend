package ms.restaurant.application.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "EAN number cannot be blank")
    private String ean;
}

