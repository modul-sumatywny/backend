package restaurant.application.dto.menuDto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuWithIdDTO {
    private Long id;

    @NotBlank(message = "Name of menu cannot be blank")
    private String name;
}
