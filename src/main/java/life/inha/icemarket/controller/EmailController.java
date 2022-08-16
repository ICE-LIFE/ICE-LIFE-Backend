package life.inha.icemarket.controller;

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
public class EmailController {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final EmailService emailService;

    private String confirm;

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
