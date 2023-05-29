package restaurant.model.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private Long id;

    @NotBlank(message = "Name of menu cannot be blank")
    private String name;

    @JsonManagedReference
    private List<DishDto> dishes;
}
