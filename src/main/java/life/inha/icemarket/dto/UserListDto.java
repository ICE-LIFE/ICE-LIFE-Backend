package life.inha.icemarket.dto;

import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListDto {
    private Integer id;

    private String name;

    private String email;
    private String nickname;

    private LocalDateTime createAt;

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
