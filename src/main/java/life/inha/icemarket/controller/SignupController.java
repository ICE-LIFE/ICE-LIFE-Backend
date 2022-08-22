package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import life.inha.icemarket.dto.UserCreateDto;
import life.inha.icemarket.exception.BadRequestException;
import life.inha.icemarket.service.EmailService;
import life.inha.icemarket.service.UserCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.regex.Pattern;


@Tag(name="Signup", description = "회원가입 API")
@Slf4j
@RequiredArgsConstructor
@Controller
public class SignupController {

    private final UserCreateService userCreateService;
    private final EmailService emailService;

    @ResponseBody
    @Operation(description = "회원가입 POST - 채워진 UserCreateDto를 받아 회원가입 처리합니다.")
    @ApiDocumentResponse
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST
    )
    public String signup(
            @ModelAttribute("UserCreateDto") @Valid UserCreateDto userCreateDto, BindingResult bindingResult
            ){
        if (bindingResult.hasErrors()){
            log.error(String.valueOf(bindingResult));
            log.error(String.valueOf(bindingResult.getAllErrors()));
            return "Binding Result Error " + bindingResult.getObjectName() + "<p>Errors: " + bindingResult.getAllErrors();
        }
        String email = userCreateDto.getEmail();

        if (!userCreateDto.getPassword1().equals(userCreateDto.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup";
        }

        userCreateService.create(
                userCreateDto.getId(),
                userCreateDto.getName(),
                userCreateDto.getEmail(),
                userCreateDto.getPassword1(),
                userCreateDto.getNickname(),
                UserRole.GUEST
        );

        return "success";
    }
    @Operation(description = "회원가입 - signup.html에 UserCreateDto를 보냅니다.")
    @ApiDocumentResponse
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("UserCreateDto", new UserCreateDto());
        return "signup";
    }
}
