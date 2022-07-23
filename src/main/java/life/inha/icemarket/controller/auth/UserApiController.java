package life.inha.icemarket.controller.auth;

import life.inha.icemarket.domain.auth.SiteUser;
import life.inha.icemarket.domain.auth.UserRepository;
import life.inha.icemarket.service.auth.UserCreateForm;
import life.inha.icemarket.service.auth.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
// @RequestMapping("/user")
public class UserApiController {
    private final UserService userService;

    private final UserRepository userRepository; //for api test

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

        //return "redirect:/";

        // for return test START
        SiteUser user = new SiteUser();
        user = userRepository.findById(12191759);
        Integer Id = user.getId();
        String Name = user.getName();
        String Email = user.getEmail();
        String Password = user.getPasswordHashed();
        String Nickname = user.getNickname();
        return Id + Name + Email + Password + Nickname;
        // for return test END
    }

    @ResponseBody // for api test
    @GetMapping("/login")
    public String login_get(){
        return "login_form";
    }

}