package life.inha.icemarket.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import life.inha.icemarket.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {
    public JwtLoginFilter(AuthenticationManager authenticationManager){
        super(authenticationManager);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        try {
            User user = (User) authResult.getPrincipal();
            String email = user.getEmail();
            Algorithm algorithm = Algorithm.HMAC256("ice-market");
            String accessToken = JWT.create()
                    .withIssuer("ice")
                    .withSubject(email)
                    .sign(algorithm);
            log.info("Create JWT : user_email : " + email);
            log.info("Login From " + email);
            response.getWriter().write(accessToken);
        } catch (JWTCreationException | IOException exception){
            exception.printStackTrace();
        }
    }
}
