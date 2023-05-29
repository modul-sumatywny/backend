package restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuPostDto {

    @NotBlank(message = "Name of menu cannot be blank")
    private String name;

    @NotNull
    private List<Long> dishesIds = new ArrayList<>();

}
