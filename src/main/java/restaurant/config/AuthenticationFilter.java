package restaurant.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(JwtDecoder jwtDecoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager) {
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String jwtString = authorizationHeader.substring(7); // Usunięcie prefiksu "Bearer " z nagłówka

                Jwt jwt = jwtDecoder.decode(jwtString);

                UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getClaim("sub"));

                Authentication authentication =
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), jwt.getClaim("pas"), userDetails.getAuthorities()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtValidationException e) {
                log.info("Token expired, generating new");
            }
        }

        filterChain.doFilter(request, response);
    }
}
