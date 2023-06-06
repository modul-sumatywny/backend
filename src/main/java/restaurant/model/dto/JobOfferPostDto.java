package restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferPostDto {

    @NotBlank(message = "Name of job offer cannot be blank")
    private String name;

    @NotBlank(message = "Description of job offer cannot be blank")
    private String description;
    @NotBlank(message = "Restaurant id cannot be blank")
    private Long restaurantId;
}
