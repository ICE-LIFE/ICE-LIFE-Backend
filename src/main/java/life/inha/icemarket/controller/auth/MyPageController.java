package life.inha.icemarket.controller.auth;

import life.inha.icemarket.domain.auth.User;
import life.inha.icemarket.dto.MyPageDto;
import life.inha.icemarket.service.auth.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private MyPageService myPageService;

    @GetMapping("/user/mypage")
    public ResponseEntity<MyPageDto> getUserInfo(User user) { // 추후 Authentication 으로 수정 필요
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<MyPageDto>(myPageService.getMyPageDto(user), header, HttpStatus.OK);
    }

}
