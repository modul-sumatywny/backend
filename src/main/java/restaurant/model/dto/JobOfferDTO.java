package restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferDTO {
    private Long id;

    @NotBlank(message = "Name of job offer cannot be blank")
    private String name;

    @NotBlank(message = "Description of job offer cannot be blank")
    private String description;

}
