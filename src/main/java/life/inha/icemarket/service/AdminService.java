package life.inha.icemarket.service;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import life.inha.icemarket.dto.UserListDto;
import life.inha.icemarket.exception.UserNotFoundException;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public List<UserListDto> getUserList(){
        return userRepository.getUserList();
    }

    public Integer convertGuestToUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(userId));
        if(user.getRole().equals(UserRole.GUEST)) {
            user.setRole(UserRole.USER);
            userRepository.save(user);
        }
        return user.getId();
    }

    public Integer grantAdmin(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        user.setRole(UserRole.ADMIN);
        userRepository.save(user);
        return user.getId();
    }

    public Integer depriveAdmin(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (user.getRole().equals(UserRole.ADMIN)) {
            user.setRole(UserRole.USER);
            userRepository.save(user);
        }
        return user.getId();
    }
}
