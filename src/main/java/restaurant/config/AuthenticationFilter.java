package restaurant.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import restaurant.model.RequestLog;
import restaurant.repository.mongo.RequestLogRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtDecoder jwtDecoder;

    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    private final RequestLogRepository requestLogRepository;

    public AuthenticationFilter(JwtDecoder jwtDecoder, UserDetailsService userDetailsService, AuthenticationManager authenticationManager, RequestLogRepository requestLogRepository) {
        this.jwtDecoder = jwtDecoder;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.requestLogRepository = requestLogRepository;
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
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        filterChain.doFilter(wrappedRequest, wrappedResponse);

        String url = wrappedRequest.getRequestURL().toString();
        String method = wrappedRequest.getMethod();
        String requestPayload = getRequestPayload(wrappedRequest);
        String responsePayload = getResponsePayload(wrappedResponse);
        try {
            saveRequestLog(url,method,requestPayload,responsePayload);
        } catch (Exception e) {
            log.error("Logging request answer to database failed");
        }
        wrappedResponse.copyBodyToResponse();
    }

    private String getRequestPayload(ContentCachingRequestWrapper request) throws UnsupportedEncodingException {
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            return new String(content, StandardCharsets.UTF_8);
        }
        return null;
    }

    private String getResponsePayload(ContentCachingResponseWrapper response) throws UnsupportedEncodingException {
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            return new String(content, StandardCharsets.UTF_8);
        }
        return null;
    }

    private void saveRequestLog(String url, String method, String requestPayload, String responsePayload) {
        RequestLog requestLog = new RequestLog();
        requestLog.setUrl(url);
        requestLog.setMethod(method);
        requestLog.setRequest(requestPayload);
        requestLog.setResponse(responsePayload);
        requestLogRepository.save(requestLog);
    }
}
