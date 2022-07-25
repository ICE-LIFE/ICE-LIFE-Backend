package life.inha.icemarket.service.auth;

import life.inha.icemarket.domain.auth.SiteUser;
import life.inha.icemarket.domain.auth.UserRepository;
import life.inha.icemarket.domain.auth.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser create(
            Integer id,
            String name,
            String email,
            String passwordHashed,
            String nickname){
        SiteUser user = new SiteUser();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPasswordHashed(passwordEncoder.encode(passwordHashed));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(UserRole.USER);
        this.userRepository.save(user);
        return user;
    }

    public void SetPasswordHashed(SiteUser user,  String passwordHashed) throws Exception{
        user.setPasswordHashed(passwordHashed);
        userRepository.save(user);
    }
}
