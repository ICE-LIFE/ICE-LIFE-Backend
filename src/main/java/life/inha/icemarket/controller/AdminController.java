package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
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


@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @Operation(description = "가입 신청자 목록 조회")
    @GetMapping("/userList")
    public ResponseEntity<UserListDto> getUserList(){
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        return new ResponseEntity<UserListDto>(adminService.getUserList(), header, HttpStatus.OK);
    }


    @Operation(description = "가입자 승인 및 거절")
    @GetMapping("/userList/allowUser")
    public ResponseEntity<Integer> allowUser(Integer userId){
        return new ResponseEntity<>(adminService.allowUser(userId), HttpStatus.OK);
    }
}

