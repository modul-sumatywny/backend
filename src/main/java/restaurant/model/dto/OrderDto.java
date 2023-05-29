package restaurant.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;

    private LocalDateTime orderTime;

    @NotNull
    private String orderStatus;

    @NotNull(message = "Restaurant ID must not be null")
    private RestaurantDto restaurant;

    @JsonManagedReference
    private List<DishDto> dishes = new ArrayList<>();
}
