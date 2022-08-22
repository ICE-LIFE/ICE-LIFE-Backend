package life.inha.icemarket.config;

import life.inha.icemarket.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserSecurityService userSecurityService;
    private final JwtDecodeFilter jwtDecodeFilter;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userSecurityService);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        JwtLoginFilter jwtLoginFilter = new JwtLoginFilter(authenticationManager);
        jwtLoginFilter.setUsernameParameter("email");
        jwtLoginFilter.setPasswordParameter("password");

        return http
                .httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .cors()
                .and()
                .authorizeRequests()
                .antMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/emailconfirm").permitAll()
                .antMatchers("/findpw").permitAll()
                .antMatchers("/resetpw").permitAll()
                .antMatchers("/onlyuser").hasRole("USER")
                .antMatchers("/onlyadmin").hasRole("ADMIN")
                .antMatchers("/ws/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtDecodeFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAt(jwtLoginFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
