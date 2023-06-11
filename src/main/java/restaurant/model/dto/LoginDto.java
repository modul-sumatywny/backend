package restaurant.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import restaurant.model.Role;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {
    private Long id;

    private String email;

    private boolean isEnabled = true;

    Collection<? extends GrantedAuthority> authorities;

    private boolean credentialsNonExpired;

    private boolean accountNonExpired;

    private boolean accountLocked;

    private String autorizationToken;

}
