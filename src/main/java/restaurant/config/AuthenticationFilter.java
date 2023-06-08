package restaurant.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.web.util.WebUtils;
import restaurant.model.RequestLog;
import restaurant.repository.mongo.RequestLogRepository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RequestLogRepository requestLogRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie cookie = WebUtils.getCookie(request, "opinionCollector");
        if (!request.getRequestURI().equals("/login") ) {
            try {
                if (cookie != null) {
                    String jwtString = cookie.getValue();

                    Jwt jwt = jwtDecoder.decode(jwtString);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(jwt.getClaim("sub"));

                    Authentication authentication =
                            authenticationManager.authenticate(
                                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), jwt.getClaim("pas"), userDetails.getAuthorities()));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JwtValidationException e) {
                log.info("Token expired, generating new");
            }

        }

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
            log.info("Logging request answer to database failed");
        }
    }

    private String getRequestPayload(ContentCachingRequestWrapper request) throws UnsupportedEncodingException {
        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            return new String(content, request.getCharacterEncoding());
        }
        return null;
    }

    private String getResponsePayload(ContentCachingResponseWrapper response) throws UnsupportedEncodingException {
        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            return new String(content, response.getCharacterEncoding());
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
