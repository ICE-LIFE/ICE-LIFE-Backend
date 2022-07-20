package life.inha.icemarket.service.auth;

import life.inha.icemarket.domain.auth.User;
import life.inha.icemarket.dto.MyPageDto;
import org.springframework.stereotype.Service;

@Service
public class MyPageService {
    public MyPageDto getMyPageDto(User user) {
        return MyPageDto.builder()
                .name(user.getName())
                .id(user.getId())
                .nickname(user.getNickname())
                .postList(user.getPostList())
                .build();
    }
}
