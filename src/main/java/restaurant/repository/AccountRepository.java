package restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import restaurant.model.Account;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
    List<Account> findByIsEnabled(boolean enable);
}
