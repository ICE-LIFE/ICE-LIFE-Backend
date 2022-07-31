package life.inha.icemarket.dto;

import life.inha.icemarket.domain.Role;
import life.inha.icemarket.domain.Status;
import life.inha.icemarket.domain.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class UserListDto {
    private Integer id;

    private String name;

    private String nickname;

    private Instant createdAt;

    private Status status;

    private Role role;

    public User toEntity(){
        return User.builder()
                .id(id)
                .name(name)
                .nickname(nickname)
                .status(status)
                .role(role)
                .build();
    }
}
