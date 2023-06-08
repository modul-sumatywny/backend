package restaurant.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import restaurant.model.Account;
import restaurant.model.Dish;
import restaurant.model.Role;
import restaurant.model.mapper.MapperBase;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto  {

    private Long id;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private boolean isEnabled = true;

    private Role role = Role.CLIENT;

    private String password;

}
