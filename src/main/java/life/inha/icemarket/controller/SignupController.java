package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.service.UserCreateService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Tag(name="Signup", description = "회원가입 API")
@Slf4j
@RequiredArgsConstructor
@Controller
public class SignupController {

    @AllArgsConstructor
    @Getter
    @Setter
    public static class UserCreateForm {

        public UserCreateForm(){

        }

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

    private final UserCreateService userCreateService;

    @Operation(description = "회원가입")
    @ApiDocumentResponse
    @ResponseBody //for api test
    @RequestMapping(
            value = "/signup",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String signup(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserCreateForm userCreateForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "Binding Result Error" + bindingResult.getObjectName();
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())){
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        userCreateService.create(
                userCreateForm.getId(),
                userCreateForm.getName(),
                userCreateForm.getEmail(),
                userCreateForm.getPassword1(),
                userCreateForm.getNickname()
        );
        return "success";
    }
}
