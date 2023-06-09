package restaurant.model;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class AccountDetails implements UserDetails {
    private final Account account;

    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    public Long getId(){
        return account.getId();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return account.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return account.isEnabled();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return account.isEnabled();
    }

    @Override
    public boolean isEnabled() {
        return account.isEnabled();
    }
}
