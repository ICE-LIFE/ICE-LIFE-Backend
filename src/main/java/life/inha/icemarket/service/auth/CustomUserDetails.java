package life.inha.icemarket.service.auth;

// UserSecurityService.java의 UserDetailsService를 implements하는 과정에서
// 반드시 Override해야하는 loadUserByUsername 메서드의 username부분을 이메일로 바꾸기 위해 UserDetails를 Custom하는 클래스이다.

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomUserDetails extends User {
    public CustomUserDetails(String email , String password, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, true, true, true, true, authorities);
    }
}
