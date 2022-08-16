package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import life.inha.icemarket.dto.EmailDto;
import life.inha.icemarket.exception.BadRequestException;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.service.EmailService;
import life.inha.icemarket.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@Tag(name="EmailConfirm", description = "이메일 인증 API")
public class EmailController {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final EmailService emailService;

    private String confirm;
    @Operation(description = "회원가입 시 이메일 전송 페이지 - 회원가입 페이지에서 EmailDto를 받아옵니다.")
    @ApiDocumentResponse
    @GetMapping("/emailconfirm")
    public String emailConfirm(@ModelAttribute("EmailDto") EmailDto emailDto, Model model) throws Exception{
        String email = emailDto.getEmail();
        System.out.println(email);
        confirm = emailService.sendSimpleMessage(email);
        System.out.println("회원가입한 이메일 : " + email);
        System.out.println("부여한 인증코드 : " + confirm);
        model.addAttribute("EmailDto",emailDto);
        return "ConfirmEmail";
    }
    @Operation(description = "회원가입 시 이메일 인증 페이지 - EmailDto를 받아옵니다.")
    @ApiDocumentResponse
    @ResponseBody
    @RequestMapping(
            value="/emailconfirm",
            method=RequestMethod.POST
    )
    public String emailConfirm(
            @ModelAttribute("EmailDto")
            @Valid EmailDto emailDto,
            BindingResult bindingResult) throws Exception{

        if (bindingResult.hasErrors()) {
            throw new BadRequestException("EmailDto 형식에 맞지 않습니다");
        }

        System.out.println("입력받은 인증코드 : " + emailDto.getInputcode());
        User user = this.userRepository.findByEmail(emailDto.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("Can't find user by email @ EmailController"));

        if(confirm.equals(emailDto.getInputcode())){
            userRoleService.SetUserRole(user,UserRole.USER);
        }
        return user.getAuthorities().toString();
    }
}
