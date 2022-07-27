package life.inha.icemarket.service.auth;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ResetPasswordForm {
    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password1;

    @NotNull(message = "비밀번호를 입력해주세요.")
    private String password2;
}
