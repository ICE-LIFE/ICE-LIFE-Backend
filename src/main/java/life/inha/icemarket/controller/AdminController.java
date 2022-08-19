package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import life.inha.icemarket.domain.Comment;
import life.inha.icemarket.domain.Post;
import life.inha.icemarket.dto.UserListDto;
import life.inha.icemarket.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.List;


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(description = "가입 신청자 목록 조회")
    @GetMapping("/userList")
    public ResponseEntity<List<UserListDto>> getUserList(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<List<UserListDto>>(adminService.getUserList(), header, HttpStatus.OK);
    }


    @Operation(description = "가입자 승인 및 거절")
    @GetMapping("/userList/allowUser")
    public ResponseEntity<Integer> allowUser(Integer userId){
        return new ResponseEntity<>(adminService.convertGuestToUser(userId), HttpStatus.OK);
    }
    @Operation(description = "가입자를 사이트에서 거절된 상태로 변경")
    @GetMapping("/userList/rejectUser")
    public ResponseEntity<Integer> rejectUser(Integer userId) {
        return new ResponseEntity<Integer>(adminService.rejectUser(userId), HttpStatus.OK);
    }

    @Operation(description = "관리자 권한 부여")
    @GetMapping("/userList/grantAdmin")
    public ResponseEntity<Integer> grantAdmin(Integer userId) {
        return new ResponseEntity<Integer>(adminService.grantAdmin(userId), HttpStatus.OK);
    }

    @Operation(description = "관리자 권한 박탈")
    @GetMapping("/userList/depriveAdmin")
    public ResponseEntity<Integer> depriveAdmin(Integer userId){
        return new ResponseEntity<Integer>(adminService.depriveAdmin(userId), HttpStatus.OK);
    }

    @Operation(description = "회원 게시물 조회")
    @GetMapping("/userPostList")
    public ResponseEntity<List<Post>> getPostsByUser(Integer userId) {
        return new ResponseEntity<>(adminService.getPostsByUser(userId), HttpStatus.OK);
    }

    @Operation(description = "회원 댓글 조회")
    @GetMapping("/userCommentList")
    public ResponseEntity<List<Comment>> getCommentsByUser(Integer userId) {
        return new ResponseEntity<>(adminService.getCommentsByUser(userId), HttpStatus.OK);
    }
}
