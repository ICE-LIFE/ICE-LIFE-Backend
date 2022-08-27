package life.inha.icemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@AllArgsConstructor
@Getter
@Setter
public class UserCreateDto {
    public UserCreateDto(){

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
    @Pattern(regexp = "[a-z0-9]+@inha.edu")
    private String email;
}