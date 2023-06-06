package restaurant.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import restaurant.model.JobOffer;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormDto {
    private Long id;
    @NotBlank(message = "First name  cannot be blank")
    private String firstname;

    @NotBlank(message = "Last name  cannot be blank")
    private String lastname;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    private Long jobOfferId;

}
