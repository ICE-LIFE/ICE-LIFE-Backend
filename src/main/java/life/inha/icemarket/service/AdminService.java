package life.inha.icemarket.service;

import life.inha.icemarket.dto.UserListDto;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public UserListDto getUserList(){
        return userRepository.getUserList();
    }
}
