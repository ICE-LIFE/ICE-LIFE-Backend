package life.inha.icemarket.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// TODO : Deprecated WebSecurityCofnigurerAdapter 고치기

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter { // Deprecated WebSecurityConfigurerAdapter -> 나중에 바꿈
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/hello").permitAll()
                .anyRequest().authenticated();
    }
}
