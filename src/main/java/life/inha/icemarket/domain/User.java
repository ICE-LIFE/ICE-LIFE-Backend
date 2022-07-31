package life.inha.icemarket.domain;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    @Column(name = "password_hashed")
    private String passwordHashed;

    private String nickname;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @Enumerated(EnumType.STRING)
    private Role role = Role.GUEST;

    @Enumerated(EnumType.STRING)
    private Status status = Status.JOIN;

    @Builder
    public User(Integer id, @NonNull String name, String nickname, Status status, Role role) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.status = status;
        this.role = role;
    }

    public void setNickname(String nickname) {
        // 닉네임 중복 체크 후 설정
        this.nickname = nickname;
    }

}
