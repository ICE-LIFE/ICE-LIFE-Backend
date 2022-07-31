package life.inha.icemarket.domain;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;


@Getter
@NoArgsConstructor
@Entity(name = "admin")
@EntityListeners(AuditingEntityListener.class)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer admin_id;

    @NonNull
    @Column(name = "admin_password_hashed")
    private String adminPasswordHashed;

    @Column
    private Timestamp created_at;

    @Column(name = "admin_role")
    private String adminRole;

    @Column
    private String email;


    @Builder
    public Admin(Integer admin_id, String adminPasswordHashed, Timestamp created_at, String email,String adminRole) {
        this.admin_id = admin_id;
        this.adminPasswordHashed = adminPasswordHashed;
        this.email = email;
        this.adminRole = adminRole;
    }
}
