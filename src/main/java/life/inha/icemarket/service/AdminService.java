package life.inha.icemarket.service;

import life.inha.icemarket.dao.UserDao;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.UserListDto;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserRepository userRepository;

    public UserListDto getUserList() {
        List<User> userList = userRepository.findAll();
        
    }
//    private UserDao dao;
//
//    public List<UserDto> list(UserDto dto) throws Exception {
//        return dao.list(dto);
//    }
//
//    public void empowerment(UserDto dto) throws Exception {
//        dto.toEntity().setRole("Admin");
//    }
//
//    public void Approve(UserDto dto) throws Exception {
//        dto.toEntity().setRole("User");
//    }
//
//    public void Reject(UserDto dto) throws Exception {
//        dto.toEntity().setRole(("Guest"));
//    }

//    public Integer allowUser(Integer userId) {
//        // 유저 조회
//
//        // 유저 Role 변경
//
//        // 유저 학번 반환
//        return
//    }


}
