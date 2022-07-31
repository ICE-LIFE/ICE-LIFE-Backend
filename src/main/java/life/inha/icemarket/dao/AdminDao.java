package life.inha.icemarket.dao;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AdminDao {

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
