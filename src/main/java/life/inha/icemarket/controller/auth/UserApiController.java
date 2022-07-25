package life.inha.icemarket.controller.auth;

import life.inha.icemarket.domain.auth.*;
import life.inha.icemarket.service.auth.UserCreateForm;
import life.inha.icemarket.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "signup_form";
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

    @PostMapping("/findpw")
    public String findpw(@RequestParam String email, @RequestParam String nickname,
                         RedirectAttributes redirect) throws UsernameNotFoundException{
        Optional <SiteUser> email_siteUser = userRepository.findByEmail(email);

        if(email_siteUser.isEmpty()){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        SiteUser siteUser = email_siteUser.get();

        if(siteUser.getNickname().equals(nickname)){
            redirect.addFlashAttribute("email", email);
            return "redirect:/resetpw";
        } else throw new UsernameNotFoundException("사용자가 없습니다.");
    }

    // todo : 테스트 - 페이지에서 redirect 잘 되는지.
    // todo : pw form 만들기, 사용자인지 인증하기

    @ResponseBody
    @PostMapping(value="/resetpw")
    public String resetpw(@RequestParam String email, @RequestParam String password1, @RequestParam String password2)
            throws Exception {
        if(!password1.equals(password2)){
            throw new Exception("입력한 두 비밀번호가 서로 다릅니다.");
        }

        password1 = passwordEncoder.encode(password1);

        Optional <SiteUser> _siteUser = userRepository.findByEmail(email);
        if(_siteUser.isEmpty()){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }

        SiteUser siteUser = _siteUser.get();
        String beforepw = siteUser.getPasswordHashed();
        this.userService.SetPasswordHashed(siteUser, password1);
        String afterpw = siteUser.getPasswordHashed();
        return beforepw + "\n" + afterpw;
    }
}