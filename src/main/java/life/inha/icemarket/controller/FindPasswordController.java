package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.exception.BadRequestException;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.service.EmailService;
import life.inha.icemarket.service.UserCreateService;
import lombok.AllArgsConstructor;
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

import javax.naming.Binding;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Tag(name="FindPassword", description = "비밀번호 찾기 API")
@Slf4j
@RequiredArgsConstructor
@Controller
public class FindPasswordController {

    @AllArgsConstructor
    @Getter
    @Setter
    public static class FindPasswordForm {

        public FindPasswordForm(){

        }

        @NotNull(message = "이메일을 입력해주세요.")
        @Email
        private String email;
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
    public static class FindPWEmailValidCodeForm{
        @Email
        @NotNull
        private String email;

        @NotNull(message = "인증코드를 입력해주세요")
        private String validcode;
    }



    private final UserCreateService userCreateService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;


    // 비밀번호 찾기 get 요청 컨트롤러
    @Operation(description = "find_pw.html에 FindPasswordForm을 담아서 보냅니다.")
    @ApiDocumentResponse
    @ResponseBody
    @GetMapping("/findpw")
    public String findpw(Model model){
        model.addAttribute("FindPasswordForm", new FindPasswordForm());
//        return "find_pw";
        return "/findpw get success";
    }


    @ResponseBody //프론트엔드 작업을 위한
    @Operation(description = "find_pw.html이 보낸 채워진 FindPasswordForm로부터 바꿀 사용자의 이메일을 받아 인증 코드를 보내고 이메일을 담은 FindPWEmailValidCode를 findpw_code.html에 보냅니다.")
    @ApiDocumentResponse
    @RequestMapping(
            value = "/findpw",
            method = RequestMethod.POST
    )
    public String findpw(
            Model model,
            @ModelAttribute("FindPasswordForm")
            @Valid FindPasswordForm findPasswordForm,
            BindingResult bindingResult) throws Exception{

        if (bindingResult.hasErrors()){
            log.error(String.valueOf(bindingResult));
            log.error(String.valueOf(bindingResult.getAllErrors()));
            return "Binding Result Error " + bindingResult.getObjectName() + "<p>Errors: " + bindingResult.getAllErrors();
        }

        Optional <User> email_User = userRepository.findByEmail(findPasswordForm.getEmail());

        if(email_User.isEmpty()){
            log.error(findPasswordForm.getEmail()+" 이메일을 사용하는 사용자를 찾을 수 없습니다.");
            bindingResult.rejectValue("email","emailIncorrect","해당 이메일을 사용하는 사용자를 찾을 수 없습니다.");
            return "redirect:/findpw";
        }
        User user = email_User.get();
        String emailKey = emailService.CreateEmailKey(user);
        System.out.println("인증하려고 하는 사용자의 이메일 : " + user.getEmail());
        System.out.println("부여한 인증코드 : " + emailKey);
        emailService.sendSimpleMessage(user.getEmail(),false);
        FindPWEmailValidCodeForm findPWEmailValidCodeForm = new FindPWEmailValidCodeForm();
        findPWEmailValidCodeForm.setEmail(user.getEmail());
        model.addAttribute("FindPWEmailValidCodeForm",findPWEmailValidCodeForm);
//        return "findpw_code";
        return "/findpw post success";//프론트엔드 작업을 위한
    }
  
    @Operation(description = "findpw_code.html이 보낸 FindPWEmailValidCodeForm으로부터 인증코드를 받아와 인증코드가 올바른지 판단 후 ResetPasswordForm에 email정보를 담아서 resetpw.html에 보냅니다.")
    @ApiDocumentResponse
    @ResponseBody//프론트엔드 작업을 위한
    @RequestMapping(
            value="/pwvalidcheck",
            method=RequestMethod.POST
    )
    public String pwcheckcode(
            Model model,
            @Valid FindPWEmailValidCodeForm findPWEmailValidCodeForm,
            BindingResult bindingResult) throws Exception{
        String ResolvedEmailKey = emailService.loadEmailKey(findPWEmailValidCodeForm.getEmail());
        if(findPWEmailValidCodeForm.getValidcode().equals(ResolvedEmailKey)){
            System.out.println("입력받은 인증번호 : " + ResolvedEmailKey);
            ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
            resetPasswordForm.setEmail(findPWEmailValidCodeForm.getEmail());
            model.addAttribute("ResetPasswordForm",resetPasswordForm);
//            return "resetpw";
            return "resetpw post success";//프론트엔드 작업을 위한
        }else{
            log.error("FindPWEmailValidCodeForm으로 부터 받아온 인증 코드가 맞지 않습니다.");
            bindingResult.rejectValue("validcode","codeIncorrect","인증 코드가 잘못되었습니다.");
            return "redirect:/findpw";
        }
    }

    @Operation(description = "resetpw.html이 보낸 채워진 ResetPasswordForm을 받아 비밀번호를 변경합니다.")
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
        if (bindingResult.hasErrors()){
            log.error(String.valueOf(bindingResult));
            log.error(String.valueOf(bindingResult.getAllErrors()));
            return "Binding Result Error " + bindingResult.getObjectName() + "<p>Errors: " + bindingResult.getAllErrors();
        }
        log.info("resetpw post");
        if (!resetPasswordForm.getPassword1().equals(resetPasswordForm.getPassword2())) {
            log.error("입력한 두 비밀번호가 다릅니다.");
            bindingResult.rejectValue("password2","EachPasswordIncorrect","입력한 두 비밀번호가 다릅니다.");
            return bindingResult + "<p>Error : 입력한 두 비밀번호가 다릅니다.";
        }

        String password = passwordEncoder.encode(resetPasswordForm.getPassword1());

        Optional<User> _User = userRepository.findByEmail(resetPasswordForm.getEmail());
        if (_User.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        User User = _User.get();
        this.userCreateService.SetPasswordHashed(User, password);
        return User.getPasswordHashed();
    }
}