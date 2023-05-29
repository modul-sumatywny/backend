package restaurant.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import restaurant.model.AccountDetails;
import restaurant.model.dto.RegistrationDto;
import restaurant.model.dto.SignInDto;
import restaurant.service.interfaces.AccountService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AccountService accountService;

    @PostMapping("login")
    public ResponseEntity<AccountDetails> login(@RequestBody SignInDto request, HttpServletResponse response) {
        try {
            AccountDetails user = accountService.signIn(request);
            String token = accountService.generateJwtToken(user, request.password);

            Cookie cookie = new Cookie("restaurantCollector", token);
            cookie.setMaxAge(86400);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(user);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("log-out")
    public ResponseEntity<Object> loginOut(HttpServletResponse response) {

        Cookie cookie = new Cookie("restaurantCollector", "token");
        cookie.setMaxAge(0);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }

    @PostMapping("register")
    public ResponseEntity<Object> register(@RequestBody RegistrationDto request) {
        try {
            Long id = accountService.register(request);
            request.setPassword("*****");
            return ok(request);
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(ex.getMessage());
        }
    }

}
