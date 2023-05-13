package restaurant.application.dto.jobDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobDTO {
    @NotBlank(message = "Name of job cannot be blank")
    private String name;

    @NotNull(message = "Description of job cannot be null")
    private String description;
}
