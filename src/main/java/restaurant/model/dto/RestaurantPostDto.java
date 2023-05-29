package restaurant.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantPostDto {
    @NotBlank(message = "Name of restaurant cannot be blank")
    private String name;

    @NotBlank(message = "Phone number to restaurant cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Address of restaurant cannot be blank")
    private String address;

    @NotBlank(message = "Photo of restaurant cannot be blank")
    private String photo;

    @NotNull(message = "Tables Ids cannot be null!")
    private List<Long> tablesIds = new ArrayList<>();

}