package life.inha.icemarket.dto;

import life.inha.icemarket.domain.Admin;
import java.sql.Timestamp;


public class AdminDto {

    private Integer admin_id;
    private String adminPasswordHashed;
    private Timestamp created_at;
    private String adminRole;
    private String email;

    public Admin toEntity() {
        return Admin.builder()
                .admin_id(admin_id)
                .adminPasswordHashed(adminPasswordHashed)
                .created_at(created_at)
                .adminRole("Admin")
                .email(email)
                .build();
    }
}
