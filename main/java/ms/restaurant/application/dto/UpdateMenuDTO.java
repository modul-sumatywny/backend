package ms.restaurant.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMenuDTO {
    @NotBlank(message = "Name of menu cannot be blank")
    private String name;
}
