package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import life.inha.icemarket.dto.UserListDto;
import life.inha.icemarket.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.List;


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



    @GetMapping("/admin")
    public ModelAndView home(Model model) throws Exception {
        UserListDto dto = new UserListDto();
        ModelAndView mav = new ModelAndView();
        List<UserListDto> list = adminService.list(dto);

        mav.setViewName("list");
        mav.addObject("list", list);

        return mav;
    }
}


