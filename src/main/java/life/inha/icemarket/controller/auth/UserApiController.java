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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Slf4j
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

    @ResponseBody
    @GetMapping("/login")
    public String login_get(){
        return "login_form";
    }

    @GetMapping("/findpw")
    public String findpw(Model model){
        model.addAttribute("FindPasswordForm", new FindPasswordForm());
        return "find_pw";
    }


   // @ResponseBody
    @RequestMapping(
            value = "/findpw",
            method = RequestMethod.POST
    )
    public String findpw(Model model, @Valid FindPasswordForm findPasswordForm, BindingResult bindingResult,
                         RedirectAttributes redirect
                         ) throws UsernameNotFoundException{
        log.info("findpw post_");
        Optional <SiteUser> email_siteUser = userRepository.findByEmail(findPasswordForm.getEmail());

        if(email_siteUser.isEmpty()){
            return "find_pw";
        }
        SiteUser siteUser = email_siteUser.get();

        if(siteUser.getNickname().equals(findPasswordForm.getNickname())){
            log.info(siteUser.getNickname());
            log.info(siteUser.getEmail());
            //model.addAttribute("email", siteUser.getEmail());
            ResetPasswordForm resetPasswordForm = new ResetPasswordForm();
            resetPasswordForm.setEmail(siteUser.getEmail());
            model.addAttribute("ResetPasswordForm", resetPasswordForm);
//            return "OK";
            return "resetpw"; //resetpw template로 email 정보 전달.
        } else {
            return "find_pw";
        }
    }

    // todo : 테스트 - 페이지에서 redirect 잘 되는지.
    // todo : pw form 만들기, 사용자인지 인증하기

    @ResponseBody
    @RequestMapping(
            value="/resetpw",
            method=RequestMethod.POST
    )
    public String resetpw(
            //@RequestParam("email") String email,
            @Valid ResetPasswordForm resetPasswordForm,
            BindingResult bindingResult) throws  Exception{
        log.info("resetPasswordForm email : {}", resetPasswordForm.getEmail());
        log.info("resetPasswordForm password : {}", resetPasswordForm.getPassword1());
        if(bindingResult.hasErrors()){
            return "Binding Result Error " + bindingResult.getObjectName();
        }
        log.info("resetpw post");
        if(! resetPasswordForm.getPassword1().equals(resetPasswordForm.getPassword2())){
            throw new Exception("입력한 두 비밀번호가 서로 다릅니다.");
        }

        String password = passwordEncoder.encode(resetPasswordForm.getPassword1());

        Optional <SiteUser> _siteUser = userRepository.findByEmail(resetPasswordForm.getEmail());
        //Optional <SiteUser> _siteUser = userRepository.findByEmail("pkd2@gmail.com");
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