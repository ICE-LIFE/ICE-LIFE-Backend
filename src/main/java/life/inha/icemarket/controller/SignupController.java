package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import life.inha.icemarket.dto.EmailDto;
import life.inha.icemarket.dto.UserCreateDto;
import life.inha.icemarket.service.UserCreateService;
import life.inha.icemarket.service.UserRoleService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Tag(name="Signup", description = "회원가입 API")
@Slf4j
@RequiredArgsConstructor
@Controller
public class SignupController {

    private final UserCreateService userCreateService;

    @Operation(description = "회원가입")
    @ApiDocumentResponse
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST
    )
    public String signup(
            @AuthenticationPrincipal User user,
            @ModelAttribute("UserCreateDto") @Valid UserCreateDto userCreateDto, BindingResult bindingResult,
            RedirectAttributes redirectAttributes) throws Exception{
        if (bindingResult.hasErrors()){
            return "Binding Result Error " + bindingResult.getObjectName();
        }

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

        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(userCreateDto.getEmail());
        redirectAttributes.addFlashAttribute("EmailDto", emailDto);
        return "redirect:/emailconfirm"; //todo redirect
    }
    @Operation(description = "회원가입")
    @ApiDocumentResponse
    @GetMapping("/signup")
    public String signup(Model model){
        model.addAttribute("UserCreateDto", new UserCreateDto());
        return "signup";
    }
}
