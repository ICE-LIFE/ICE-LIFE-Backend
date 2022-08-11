package life.inha.icemarket.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import groovy.util.logging.Slf4j;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtDecodeFilter  extends OncePerRequestFilter {
    private final UserSecurityService userSecurityService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            try{
                String accessToken = header.substring(7);
                Algorithm algorithm = Algorithm.HMAC256("ice-market");
                JWTVerifier verifier = JWT.require(algorithm).withIssuer("ice").build();

                DecodedJWT jwt = verifier.verify(accessToken);
                String email = jwt.getSubject();
                System.out.println("Verify JWT : email=" + email);
                User user = (User) userSecurityService.loadUserByUsername(email);
//                System.out.println("getRole() : " + user.getRole());
                System.out.println("getAuthorities() : " + user.getAuthorities());
                Authentication authenticationToken
                        = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } catch(JWTVerificationException exception){
                exception.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}
