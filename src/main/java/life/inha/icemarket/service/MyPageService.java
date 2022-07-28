package life.inha.icemarket.service.auth;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.MyPageDto;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPageService {

    private final UserRepository userRepository;

    public User update(Integer userId, String nickname) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("사용자를 찾을 수 없습니다."));
        user.setNickname(nickname);
        return userRepository.save(user);
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
