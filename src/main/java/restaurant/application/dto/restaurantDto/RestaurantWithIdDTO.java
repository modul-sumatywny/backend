package restaurant.application.dto.restaurantDto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantWithIdDTO {
    private Long id;

    @NotBlank(message = "Name of restaurant cannot be blank")
    private String name;

    @NotNull(message = "Phone number to restaurant cannot be null")
    private Integer phoneNumber;

    @NotBlank(message = "Address of restaurant cannot be blank")
    private String address;

    @NotBlank(message = "Photo of restaurant cannot be blank")
    private String photo;
}