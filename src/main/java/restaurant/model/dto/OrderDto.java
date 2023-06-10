package restaurant.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    private Long id;
    @NotNull(message = "First name must not be null")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    private String lastName;
    @NotNull(message = "Phone number must not be null")
    private String phoneNumber;
    private String orderTime;
    @NotNull
    private String orderStatus;
    @NotNull
    private Integer orderTotalCost;

    private Long accountId;

    private String email;

    @NotNull(message = "Restaurant ID must not be null")
    private RestaurantDto restaurant;

    @JsonManagedReference
    private List<DishDto> dishes = new ArrayList<>();

}
