package life.inha.icemarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@Getter
@Setter
public class EmailDto{
    public EmailDto(){

    }
    @NotEmpty(message="코드를 입력해주세요.")
    private String inputcode;

    @Email
    private String email;
}
