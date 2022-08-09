package life.inha.icemarket.dto;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class UserListDto {
    private Integer id;

    private String name;

    private String email;
    private String nickname;

    private Instant createAt;

    private Status status;

    private UserRole role;

    public User toEntity(){
        return User.builder()
                .id(id)
                .name(name)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
