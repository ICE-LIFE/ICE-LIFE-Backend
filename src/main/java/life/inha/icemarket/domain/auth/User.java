package life.inha.icemarket.domain.auth;

import life.inha.icemarket.domain.core.Post;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "users")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    @Column(name = "password_hashed")
    private String passwordHashed;

    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @CreatedDate
    @NonNull
    private LocalDateTime createdAt;

//    @LastModifiedDate
//    @NonNull
//    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public User(Integer id, @NonNull String name, @NonNull String email, String nickname) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
    }

    public void setNickname(String nickname){
        // 닉네임 중복 체크 후 설정
        this.nickname = nickname;
    }
}
