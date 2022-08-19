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
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRoleService userRoleService;

    // loadUserByUsername 메서드에서는 리턴한 User 객체의 비밀번호가 화면으로부터 입력 받은 비밀번호와 일치하는지를 검사하는 로직이 있음.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("UserDetailsService : email=" + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("Can't find user by email @ UserDetails"));

//        if(user.getEmail().equals("daezang102@naver.com")){
//            userRoleService.SetUserRole(user,UserRole.ADMIN);
//        }
        return user;
    }
}
