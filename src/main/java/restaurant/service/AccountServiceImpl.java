package restaurant.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.expiration.seconds}")
    private long expirationTime;

    @Override
    @Transactional
    public Long register(RegistrationDto registrationForm) {
        boolean isValidLogin = RegistrationDto.validateEmail(registrationForm.getEmail());
        boolean checkLogin = accountRepository.findByEmail(registrationForm.getEmail()).isPresent();
        if (!isValidLogin) {
            throw new IllegalStateException("email not valid");
        }

        if (checkLogin && !accountRepository.findByEmail(registrationForm.getEmail()).get().isEnabled()) {
            Account account = accountRepository.findByEmail(registrationForm.getEmail())
                    .orElseThrow(IllegalStateException::new);
            account.setEnabled(true);
            account.setModifiedAt(LocalDateTime.now());
            accountRepository.save(account);
            return accountRepository.findByEmail(registrationForm.getEmail()).get().getId();
        } else {
            if (checkLogin) {
                throw new IllegalStateException("Email is taken by another user");
            }
        }

        return accountRepository.save(Account.builder()
                        .email(registrationForm.getEmail())
                        .role(Role.CLIENT)
                        .createdAt(LocalDateTime.now())
                        .isEnabled(true)
                        .password(bCryptPasswordEncoder.encode(registrationForm.getPassword())).build()).getId();
    }


    @Override
    public AccountDetails signIn(SignInDto singInForm) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(singInForm.getEmail(), singInForm.getPassword()));

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

    @Transactional
    public void changeEmail(String email) {
        if (RegistrationDto.validateEmail(email) && accountRepository.findByEmail(email).isEmpty()) {
            Account client = accountRepository.findByEmail(email).orElseThrow(IllegalStateException::new);
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
        Account client = accountRepository.findByEmail(username)
                .orElseThrow(IllegalStateException::new);
        client.setPassword(hashedPass);
        client.setModifiedAt(LocalDateTime.now());
        accountRepository.save(client);
    }

    @Override
    public Account getAccount(String username) {
        return accountRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalStateException("Client does not exist"));
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public void archive(String username) {
        Account client = accountRepository.findByEmail(username)
                .orElseThrow(IllegalStateException::new);
        client.setEnabled(false);
        client.setModifiedAt(LocalDateTime.now());
        accountRepository.save(client);
    }

    @Override
    public void active(String username) {
        Account client = accountRepository.findByEmail(username)
                .orElseThrow(IllegalStateException::new);
        client.setEnabled(true);
        client.setModifiedAt(LocalDateTime.now());
        accountRepository.save(client);
    }

    @Override
    public void changeRole(String username, Role role) {
        Account client = accountRepository.findByEmail(username)
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
