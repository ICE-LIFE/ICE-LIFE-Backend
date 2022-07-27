package life.inha.icemarket.controller.auth;

import life.inha.icemarket.config.JwtAuthenticationEntryPoint;
import life.inha.icemarket.config.JwtAuthenticationFilter;
import life.inha.icemarket.config.JwtTokenProvider;
import life.inha.icemarket.config.Token;
import life.inha.icemarket.domain.auth.*;
import life.inha.icemarket.service.auth.FindPasswordForm;
import life.inha.icemarket.service.auth.ResetPasswordForm;
import life.inha.icemarket.service.auth.UserCreateForm;
import life.inha.icemarket.service.auth.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class UserApiController {
    private final UserService userService;

    private final UserRepository userRepository; //for api test

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm){
        return "signup_form";
    }

    @ResponseBody //for api test
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    ) // 7/27 json request
    //@PostMapping("/signup")
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

    @ResponseBody
    @GetMapping("/login")
    public String login_get(){
        return "login_form";
    }

    @GetMapping("/findpw")
    public String findpw(){
        return "find_pw";
    }

    @RequestMapping(
            value = "/findpw",
            method = RequestMethod.POST
    )
    public String findpw(@Valid @RequestBody FindPasswordForm findPasswordForm, BindingResult bindingResult,
                         RedirectAttributes redirect
                         ) throws UsernameNotFoundException{
        Optional <SiteUser> email_siteUser = userRepository.findByEmail(findPasswordForm.getEmail());

        if(email_siteUser.isEmpty()){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        SiteUser siteUser = email_siteUser.get();

        if(siteUser.getNickname().equals(findPasswordForm.getNickname())){
        redirect.addFlashAttribute("email", findPasswordForm.getEmail());
            return "redirect:/resetpw"; //resetpw 홈페이지로 email 정보 전달.
        } else throw new UsernameNotFoundException("사용자가 없습니다.");
    }

    // todo : 테스트 - 페이지에서 redirect 잘 되는지.
    // todo : pw form 만들기, 사용자인지 인증하기

    @ResponseBody
    @RequestMapping(
            value="/resetpw",
            method=RequestMethod.POST
    )
    public String resetpw(@Valid @RequestBody ResetPasswordForm resetPasswordForm, BindingResult bindingResult) throws  Exception{
        if(bindingResult.hasErrors()){
            return "Binding Result Error" + bindingResult.getObjectName();
        }

        if(! resetPasswordForm.getPassword1().equals(resetPasswordForm.getPassword2())){
            throw new Exception("입력한 두 비밀번호가 서로 다릅니다.");
        }

        String password = passwordEncoder.encode(resetPasswordForm.getPassword1());

        Optional <SiteUser> _siteUser = userRepository.findByEmail(resetPasswordForm.getEmail());
        if(_siteUser.isEmpty()){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }

        SiteUser siteUser = _siteUser.get();
        String beforepw = siteUser.getPasswordHashed();
        this.userService.SetPasswordHashed(siteUser, password);
        String afterpw = siteUser.getPasswordHashed();
        return beforepw + "\n" + afterpw;
    }



}