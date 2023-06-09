package restaurant.controllers;


import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import restaurant.model.Account;
import restaurant.model.Role;
import restaurant.model.dto.AccountDto;
import restaurant.model.dto.StockDto;
import restaurant.model.mapper.AccountMapper;
import restaurant.model.mapper.StockMapper;
import restaurant.service.interfaces.AccountService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);


    @GetMapping("/self")
    public ResponseEntity<Object> getClient(Principal principal) {
        try {
            return ResponseEntity.ok(accountService.getAccount(principal.getName()));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<AccountDto>> getClients() {
        List<Account> accounts = accountService.getAccounts();
        List<AccountDto> accountDtos = accounts.stream()
                .map(mapper::entityToDto)
                .toList();
        return ResponseEntity.ok(accountDtos);
    }

    @PutMapping("/self/change-password")
    public ResponseEntity<Object> changePass(Principal principal, String password, HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("restaurantCollector", "token");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            accountService.changePass(principal.getName(), password);

            return ResponseEntity.ok("Password changed");
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/self/profile-deletion")
    public ResponseEntity<Object> archive(Principal principal, HttpServletResponse response) {
        try {
            Cookie cookie = new Cookie("opinionCollector", "token");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            accountService.archive(principal.getName());
            return ResponseEntity.ok("Profile deletion");
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{username}/archive")
    public ResponseEntity<Object> archiveClient(@PathVariable String username) {
        try {
            accountService.archive(username);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{username}/active")
    public ResponseEntity<Object> activeClient(@PathVariable String username) {
        try {
            accountService.active(username);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

    @PutMapping("/change-role")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Object> changeRole(String userName, Role role) {
        try {
            accountService.changeRole(userName, role);
            return ResponseEntity.ok("Role changed");
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/archived-clients")
    public ResponseEntity<Object> getAllArchivedClients() {
        try {
            return ResponseEntity.ok(accountService.getAllArchivedClients());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/active-clients")
    public ResponseEntity<Object> getAllActiveClients() {
        try {
            return ResponseEntity.ok(accountService.getAllActiveClients());
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
