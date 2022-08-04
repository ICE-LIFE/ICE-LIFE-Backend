package life.inha.icemarket.service;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;

    // loadUserByUsername 메서드에서는 리턴한 User 객체의 비밀번호가 화면으로부터 입력 받은 비밀번호와 일치하는지를 검사하는 로직이 있음.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        /*
        _User = 홈페이지 화면으로부터 받아온 user 정보 :: DB에 user 정보가 존재하는지 판단하기 위해 사용함.
         */
        Optional<User> _User = this.userRepository.findByEmail(email);

        if (_User.isEmpty()) { // 만약 입력받은 사용자 정보가 DB에 없다면
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        User User = _User.get();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // TODO : 관리자가 권한 부여 방식으로 바꾼다면, 이 부분에서 authorities 부여 방식이 달라져야 함.
        /* 로그인 시 아이디(username)이 "ADMIN"이라면, 그 사용자에게 무조건 admin 부여

        if("admin".equals(username)){    //loadByUsername() 메서드의 매개변수가 String username이어야 작동함.
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }
        */
        authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        return new CustomUserDetails(User.getEmail(), User.getPasswordHashed(), authorities);
    }
}
