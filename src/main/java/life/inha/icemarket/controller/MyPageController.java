package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.MyPageDto;
import life.inha.icemarket.service.auth.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Tag(name = "MyPage", description = "마이페이지 API")
@RestController
@RequiredArgsConstructor
public class MyPageController {
    private MyPageService myPageService;

    @Operation(description = "사용자 정보 조회")
    @GetMapping("/user/mypage/{id}")
    @ApiDocumentResponse
    public ResponseEntity<MyPageDto> getUserInfo(@PathVariable Integer userId) throws Exception { // 추후 @Autenticated로 UserId 가져오도록 수정 필요
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<MyPageDto>(myPageService.getMyPageDto(userId), header, HttpStatus.OK);
    }


    @Operation(description = "사용자 정보 수정")
    @PutMapping("/user/mypage/{id}")
    @ApiDocumentResponse
    public ResponseEntity<User> updateUserInfo(@PathVariable Integer userId, @RequestParam("nickname") String nickname) throws Exception {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<User>(myPageService.update(userId, nickname), header, HttpStatus.OK);
    }

}
