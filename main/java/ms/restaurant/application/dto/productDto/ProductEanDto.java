package ms.restaurant.application.dto.productDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductEanDto {
    @NotBlank(message = "EAN number of product cannot be blank")
    private String ean;
}
