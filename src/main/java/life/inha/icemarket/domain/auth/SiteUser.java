package life.inha.icemarket.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    @Column(name = "password_hashed")
    private String passwordHashed;

    private String nickname;

    @NonNull
    private String student_id;

    @CreatedDate
    @NonNull
    private LocalDateTime createdAt;

//    @LastModifiedDate
//    @NonNull
//    private LocalDateTime updatedAt;
//
//    @Column(name = "deleted_at")
//    private LocalDateTime deletedAt;
}
