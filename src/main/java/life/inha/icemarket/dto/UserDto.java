package life.inha.icemarket.dto;


import life.inha.icemarket.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String name;
    private String email;
    private String nickname;

    private Integer authority = 1;

    public User toEntity() {
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .nickname(nickname)
                .role("Guest")
                .build();
    }
}
