package life.inha.icemarket.domain;

import life.inha.icemarket.dto.Status;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.sql.Array;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
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

    @Column(name = "deleted_at")
    private Instant deletedAt;


    @OneToMany(mappedBy = "user")
    private List<Post> postList;

    @Transient
    @Enumerated(EnumType.STRING)
    private UserRole role = UserRole.GUEST;

    @Transient
    @Enumerated(EnumType.STRING)
    private Status status = Status.JOIN;

    @Builder
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(this.role.getValue()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHashed;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
