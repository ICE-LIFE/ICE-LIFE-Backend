package life.inha.icemarket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Responding with unathorized error : {}",authException.getMessage());

        request.setAttribute("response.failure.code", request.getAttribute("unauthorization.code"));
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, (String) request.getAttribute("unathorization.code"));
    }
}
