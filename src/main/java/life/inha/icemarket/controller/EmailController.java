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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Operation(description = "Email 인증 페이지입니다. ConfirmEmail.html에 EmailDto를 담아서 보냅니다. EmailDto의 Email값은 JWT를 이용해 로그인한 사용자의 정보에서 가져옵니다.")
    @ApiDocumentResponse
    @GetMapping("/emailconfirm")
    public String emailConfirm(@AuthenticationPrincipal User user, Model model) throws Exception{
        // emailController에 get요청을 할때마다 이 사용자에게 새로운 이메일 인증코드를 발급해주고 이메일로 전송합니다.
        String emailKey = emailService.CreateEmailKey(user);
        System.out.println("인증하려고 하는 사용자의 이메일 : " + user.getEmail());
        System.out.println("부여한 인증코드 : " + emailKey);
        emailService.sendSimpleMessage(user.getEmail());
        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(user.getEmail());
        model.addAttribute("EmailDto",emailDto);
        return "ConfirmEmail";
    }
    @Operation(description = "POST 이메일 인증 페이지 - EmailDto를 받아옵니다. 또 인증코드가 올바르다면 권한을 승급합니다.")
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

        String emailKey = emailService.loadEmailKey(emailDto.getEmail());

        if (bindingResult.hasErrors()) {
            throw new BadRequestException("EmailDto 형식에 맞지 않습니다");
        }

        System.out.println("입력받은 인증코드 : " + emailDto.getInputcode());
        User user = this.userRepository.findByEmail(emailDto.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("Can't find user by email @ EmailController"));

        if(emailKey.equals(emailDto.getInputcode())) {
            userRoleService.SetUserRole(user, UserRole.USER);
        }
        return user.getAuthorities().toString();

    }
}
