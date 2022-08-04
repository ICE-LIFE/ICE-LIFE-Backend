package life.inha.icemarket.controller;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Controller


public class UserApiController {

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

    @Getter
    @Setter
    public static class UserCreateForm {
        @NotNull(message = "사용자 ID는 필수항목입니다.")
        private Integer id;

        @Size(min=3, max=25)
        @NotEmpty(message = "사용자 이름은 필수항목입니다.")
        private String name;

        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        private String password1;

        @NotEmpty(message = "비밀번호는 필수항목입니다.")
        private String password2;

        @NotEmpty(message = "사용자 별명은 필수항목입니다.")
        private String nickname;

        @NotEmpty(message = "사용자 이메일은 필수항목입니다.")
        @Email
        private String email;
    }

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // 회원가입 post 요청 컨트롤러 시작 //
    @ResponseBody //for api test
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String signup(
            @Valid @RequestBody UserCreateForm userCreateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "Binding Result Error" + bindingResult.getObjectName();
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        userService.create(
                userCreateForm.getId(),
                userCreateForm.getName(),
                userCreateForm.getEmail(),
                userCreateForm.getPassword1(),
                userCreateForm.getNickname()
        );
        return "redirect:/";
    }
    // 회원가입 post 요청 컨트롤러 끝 //

    // 로그인 get 요청 컨트롤러 //
    @ResponseBody
    @GetMapping("/login")
    public String login_get(){
        return "login_form";
    }


    // 비밀번호 찾기 get 요청 컨트롤러
    @GetMapping("/findpw")
    public String findpw(Model model){
        model.addAttribute("FindPasswordForm", new FindPasswordForm());
        return "find_pw";
    }


    // 비밀번호 찾기 post 요청 컨트롤러 시작 //
   // @ResponseBody
    @RequestMapping(
            value = "/findpw",
            method = RequestMethod.POST
    )
    public String findpw(Model model, @Valid FindPasswordForm findPasswordForm, BindingResult bindingResult,
                         RedirectAttributes redirect
                         ) throws UsernameNotFoundException{
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
        this.userService.SetPasswordHashed(User, password);
        String afterpw = User.getPasswordHashed();
        return beforepw + "\n" + afterpw;
    }
    // 비밀번호 초기화 post 요청 컨트롤러 끝 //
}