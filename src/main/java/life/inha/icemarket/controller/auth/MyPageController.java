package life.inha.icemarket.controller.auth;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.domain.auth.User;
import life.inha.icemarket.dto.MyPageDto;
import life.inha.icemarket.service.auth.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@Tag(name = "MyPage", description = "마이페이지")
@RestController
@RequiredArgsConstructor
public class MyPageController {
    private MyPageService myPageService;

    @GetMapping("/user/mypage")
    @ApiResponses({
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "잘못된 유저 조회 URL 요청"),
            @ApiResponse(responseCode = "403", description = "클라이언트의 접근 권한이 없음")
    })
    public ResponseEntity<MyPageDto> getUserInfo(Integer userId) throws Exception { // 추후 @Autenticated로 UserId 가져오도록 수정 필요
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<MyPageDto>(myPageService.getMyPageDto(userId), header, HttpStatus.OK);
    }


    @PutMapping("/user/mypage")
    public ResponseEntity<Integer> updateUserInfo(String nickname){
        return new ResponseEntity<>(myPageService.update(nickname), HttpStatus.OK);
    }

}
