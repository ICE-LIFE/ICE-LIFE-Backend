package life.inha.icemarket.service.auth;

import life.inha.icemarket.domain.auth.SiteUser;
import life.inha.icemarket.domain.auth.UserRepository;
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
        this.userRepository.save(user);
        return user;
    }
}
