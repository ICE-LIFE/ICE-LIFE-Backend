package life.inha.icemarket.config;

import life.inha.icemarket.service.auth.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final UserSecurityService userSecurityService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
//                .antMatchers("/hello").permitAll()
//                .antMatchers("/signup").permitAll()
//                .antMatchers("/login").permitAll()
                .antMatchers("/**/").permitAll()
                .and()
                    .csrf()
                        .disable()
                .formLogin()
                    .usernameParameter("email")
               // .loginPage("/login")
                // .defaultSuccessUrl("/")
                ;
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean // 이 Bean을 생성하면, 스프링의 내부 동작에서 UserSecurityService와 PasswordEncoder가 자동으로 설정된다.
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
}
