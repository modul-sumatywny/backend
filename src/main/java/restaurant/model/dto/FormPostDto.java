package restaurant.model.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FormPostDto {

    @NotBlank(message = "First name  cannot be blank")
    private String firstname;

    @NotBlank(message = "Last name  cannot be blank")
    private String lastname;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Job offer id cannot be blank")
    private Long jobOfferId;
}
