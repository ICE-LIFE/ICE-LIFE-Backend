package life.inha.icemarket.service;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import life.inha.icemarket.respository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRoleService {
    private final UserRepository userRepository;
    public void SetUserRole(User user, UserRole userRole){
        user.setRole(userRole);
        this.userRepository.save(user);
    }
}
