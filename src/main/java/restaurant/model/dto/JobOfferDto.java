package restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.model.Restaurant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobOfferDto {
    private Long id;

    @NotBlank(message = "Name of job offer cannot be blank")
    private String name;

    @NotBlank(message = "Description of job offer cannot be blank")
    private String description;

    private RestaurantDto restaurant;
}
