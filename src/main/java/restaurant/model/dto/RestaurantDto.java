package restaurant.model.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.model.Menu;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDto {

    private Long id;

    @NotBlank(message = "Name of restaurant cannot be blank")
    private String name;

    @NotBlank(message = "Phone number to restaurant cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Address of restaurant cannot be blank")
    private String address;

    @NotBlank(message = "Photo of restaurant cannot be blank")
    private String photo;

    private long menu_id;

}