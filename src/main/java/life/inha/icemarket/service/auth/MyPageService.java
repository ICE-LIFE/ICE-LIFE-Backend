package life.inha.icemarket.service.auth;

import life.inha.icemarket.domain.auth.User;
import life.inha.icemarket.domain.auth.UserRepository;
import life.inha.icemarket.dto.MyPageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private UserRepository userRepository;

    public Integer update(Integer userId, String nickname) throws Exception{
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));
        user.setNickname(nickname);
        return userRepository.save(user).getId();
    }

    public MyPageDto getMyPageDto(Integer userId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));
        return MyPageDto.builder()
                .name(user.getName())
                .id(user.getId())
                .nickname(user.getNickname())
                .postList(user.getPostList())
                .build();
    }
}
