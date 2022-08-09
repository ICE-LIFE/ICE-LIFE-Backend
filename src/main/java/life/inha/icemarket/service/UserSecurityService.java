package life.inha.icemarket.service;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    // loadUserByUsername 메서드에서는 리턴한 User 객체의 비밀번호가 화면으로부터 입력 받은 비밀번호와 일치하는지를 검사하는 로직이 있음.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        System.out.println("UserDetailsService : email=" + email);
        Optional<User> _User = this.userRepository.findByEmail(email);

        if (_User.isEmpty()) { // 만약 입력받은 사용자 정보가 DB에 없다면
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        User user = _User.get();

        // TODO : 관리자가 권한 부여 방식으로 바꾼다면, 이 부분에서 authorities 부여 방식이 달라져야 함.
        UserRole userRole = UserRole.USER;
        //
        if(user.getEmail().equals("pkd2@gmail.com")){
            user.setRole(UserRole.ADMIN);
        } else user.setRole(userRole);
        return user;
    }
}
