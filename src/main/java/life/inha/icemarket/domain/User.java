package life.inha.icemarket.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    // User의 Id는 학번이므로 자동 생성 기능을 꺼두었습니다.
    private Integer id;


    @Column(unique=true)
    private String name;

    @Email
    @Column(unique=true)
    private String email;

    @NonNull
    @Column(name = "password_hashed")
    private String passwordHashed;

    private String nickname;

    @CreatedDate
    private Instant createdAt;

//    @LastModifiedDate
//    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;


    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @Transient
    private UserRole role;

    public User(Integer id, @NonNull String name, @NonNull String email, String nickname) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
    }

    public void setNickname(String nickname) {
        // 닉네임 중복 체크 후 설정
        this.nickname = nickname;
    }

}
