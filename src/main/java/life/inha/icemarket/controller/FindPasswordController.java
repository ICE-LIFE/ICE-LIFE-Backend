package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.service.UserCreateService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Tag(name="FindPassword", description = "비밀번호 찾기 API")
@Slf4j
@RequiredArgsConstructor
@Controller

public class FindPasswordController {

    @Getter
    @Setter
    public static class FindPasswordForm {
        @NotNull(message = "이메일을 입력해주세요.")
        @Email
        private String email;

        @NotNull(message = "닉네임을 입력해주세요.")
        private String nickname;
    }

    @Getter
    @Setter
    public static class ResetPasswordForm {
        @NotNull(message = "이메일을 입력해주세요.")
        @Email
        private String email;

        @NotNull(message = "비밀번호를 입력해주세요.")
        private String password1;

        @NotNull(message = "비밀번호를 입력해주세요.")
        private String password2;
    }



    private final UserCreateService userCreateService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 비밀번호 찾기 get 요청 컨트롤러
    @Operation(description = "비밀번호 찾기 페이지")
    @ApiDocumentResponse
    @GetMapping("/findpw")
    public String findpw(Model model){
        model.addAttribute("FindPasswordForm", new FindPasswordForm());
        return "find_pw";
    }


    // 비밀번호 찾기 post 요청 컨트롤러 시작 //
    @Operation(description = "비밀번호 찾기")
    @ApiDocumentResponse
   // @ResponseBody
    @RequestMapping(
            value = "/findpw",
            method = RequestMethod.POST
    )
    public String findpw(Model model,@ModelAttribute("FindPasswordForm") @Valid FindPasswordForm findPasswordForm, BindingResult bindingResult) throws UsernameNotFoundException{
        log.info("findpw post_");
        Optional <User> email_User = userRepository.findByEmail(findPasswordForm.getEmail());

        if(email_User.isEmpty()){
            return "find_pw";
        }
        User User = email_User.get();
        if(User.getNickname().equals(findPasswordForm.getNickname())){
            log.info(User.getNickname());
            log.info(User.getEmail());
            ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
            resetPasswordForm.setEmail(User.getEmail());
            model.addAttribute("ResetPasswordForm", resetPasswordForm);
            return "resetpw"; //resetpw template로 email 정보 전달.
        } else {
            return "find_pw";
        }
    }
    // 비밀번호 찾기 post 요청 컨트롤러 끝 //

    // 비밀번호 초기화 post 요청 컨트롤러 시작 //
    @Operation(description = "비밀번호 초기화")
    @ApiDocumentResponse
    @ResponseBody
    @RequestMapping(
            value="/resetpw",
            method=RequestMethod.POST
    )
    public String resetpw(
            @Valid ResetPasswordForm resetPasswordForm,
            BindingResult bindingResult) throws  Exception {
        log.info("resetPasswordForm email : {}", resetPasswordForm.getEmail());
        log.info("resetPasswordForm password : {}", resetPasswordForm.getPassword1());
        if (bindingResult.hasErrors()) {
            return "Binding Result Error " + bindingResult.getObjectName();
        }
        log.info("resetpw post");
        if (!resetPasswordForm.getPassword1().equals(resetPasswordForm.getPassword2())) {
            throw new Exception("입력한 두 비밀번호가 서로 다릅니다.");
        }

        String password = passwordEncoder.encode(resetPasswordForm.getPassword1());

        Optional<User> _User = userRepository.findByEmail(resetPasswordForm.getEmail());
        if (_User.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        User User = _User.get();
        String beforepw = User.getPasswordHashed();
        this.userCreateService.SetPasswordHashed(User, password);
        String afterpw = User.getPasswordHashed();
        return beforepw + "\n" + afterpw;
    }
    // 비밀번호 초기화 post 요청 컨트롤러 끝 //
}