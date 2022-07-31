package life.inha.icemarket.service;

import life.inha.icemarket.dao.UserDao;
import life.inha.icemarket.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class AdminService {

    private UserDao dao;

    public List<UserDto> list(UserDto dto) throws Exception {
        return dao.list(dto);
    }

    public void Empowerment(UserDto dto) throws Exception {
        dto.toEntity().setRole("Admin");
    }

    public void Approve(UserDto dto) throws Exception {
        dto.toEntity().setRole("User");
    }

    public void Reject(UserDto dto) throws Exception {
        dto.toEntity().setRole(("Guest"));
    }

}
