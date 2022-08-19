package life.inha.icemarket.service;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserCreateService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User create(
            Integer id,
            String name,
            String email,
            String passwordHashed,
            String nickname,
            UserRole userRole){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPasswordHashed(passwordEncoder.encode(passwordHashed));
        user.setRole(userRole);
        this.userRepository.save(user);
        return user;
    }

    public void SetPasswordHashed(User user,  String passwordHashed) throws Exception{
        user.setPasswordHashed(passwordHashed);
        userRepository.save(user);
    }

}
