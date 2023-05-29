package restaurant.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import restaurant.model.Account;
import restaurant.model.AccountDetails;
import restaurant.model.Role;
import restaurant.model.dto.RegistrationDto;
import restaurant.model.dto.SignInDto;
import restaurant.repository.AccountRepository;
import restaurant.service.interfaces.AccountService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    @Value("${jwt.expiration.seconds}")
    private long expirationTime;

    @Override
    @Transactional
    public Long register(RegistrationDto registrationForm) {
        boolean isValidEmail = RegistrationDto.validateEmail(registrationForm.getEmail());
        boolean isValidLogin = registrationForm.validateUsername(registrationForm.getUsername());
        boolean checkEmail = accountRepository.findByEmail(registrationForm.getEmail()).isPresent();
        boolean checkLogin = accountRepository.findByUsername(registrationForm.getUsername()).isPresent();
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }

        if (!isValidLogin) {
            throw new IllegalStateException("login not valid");
        }

        if (checkLogin && checkEmail && !accountRepository.findByUsername(registrationForm.getUsername()).get().isEnabled()) {
            Account account = accountRepository.findByUsername(registrationForm.getUsername())
                    .orElseThrow(IllegalStateException::new);
            account.setEnabled(true);
            account.setModifiedAt(LocalDateTime.now());
            accountRepository.save(account);
            return accountRepository.findByUsername(registrationForm.getUsername()).get().getId();
        } else {
            if (checkLogin) {
                throw new IllegalStateException("clientExist");
            }

            if (checkEmail) {
                throw new IllegalStateException("Email is taken by another user.");
            }
        }

        return accountRepository.save(
                new Account(registrationForm.getUsername(),
                        registrationForm.getFirstName(),
                        registrationForm.getLastName(),
                        registrationForm.getEmail(),
                        registrationForm.getPhoneNumber(),
                        registrationForm.getHashedPass())
        ).getId();
    }

    @Override
    public AccountDetails signIn(SignInDto singInForm) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(singInForm.getUsername(), singInForm.getPassword()));

        return (AccountDetails) authentication.getPrincipal();
    }

    @Override
    public String generateJwtToken(AccountDetails accountDetails, String password) {
        Instant now = Instant.now();
        long expiry = expirationTime;

        String roles = accountDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));

        JwtClaimsSet claims =
                JwtClaimsSet.builder()
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(expiry))
                        .subject(accountDetails.getUsername())
                        .claim("scp", roles)
                        .claim("pas", password)
                        .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    @Override
    @Transactional
    public void changeEmail(String username, String email) {
        if (RegistrationDto.validateEmail(email) && accountRepository.findByEmail(email).isEmpty()) {
            Account client = accountRepository.findByUsername(username).orElseThrow(IllegalStateException::new);
            client.setEmail(email);
            client.setModifiedAt(LocalDateTime.now());
            accountRepository.save(client);
        } else {
            throw new IllegalStateException("Wrong email.");
        }
    }

    @Override
    @Transactional
    public void changePass(String username, String hashedPass) {
        Account client = accountRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        client.setPassword(hashedPass);
        client.setModifiedAt(LocalDateTime.now());
        accountRepository.save(client);
    }

    @Override
    public Account getAccount(String username) {
        return accountRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("Client does not exist"));
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void archive(String username) {
        Account client = accountRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        client.setEnabled(false);
        client.setModifiedAt(LocalDateTime.now());
        accountRepository.save(client);
    }

    @Override
    public void active(String username) {
        Account client = accountRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        client.setEnabled(true);
        client.setModifiedAt(LocalDateTime.now());
        accountRepository.save(client);
    }

    @Override
    public void changeRole(String username, Role role) {
        Account client = accountRepository.findByUsername(username)
                .orElseThrow(IllegalStateException::new);
        client.setRole(role);
        client.setModifiedAt(LocalDateTime.now());
        accountRepository.save(client);
    }

    @Override
    public List<Account> getAllArchivedClients() {
        return accountRepository.findByIsEnabled(false);
    }

    @Override
    public List<Account> getAllActiveClients() {
        return accountRepository.findByIsEnabled(true);
    }
}
