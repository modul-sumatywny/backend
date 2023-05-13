package restaurant.application.dto.jobOfferDto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobOfferWithIdDTO {
    private Long id;
    @NotNull(message = "Restaurant ID cannot be null")
    private Long restaurantId;

    @NotNull(message = "Job ID cannot be null")
    private Long jobId;

    @NotNull(message = "Part-time of job offer cannot be null")
    private String partTime;

    @NotNull(message = "Salary of job offer cannot be null")
    private Float salary;

    @NotNull(message = "Salary of job offer cannot be null")
    private String status;

}
