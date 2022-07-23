package life.inha.icemarket.domain.core;

import lombok.Getter;

@Getter
public enum UserRole {
    GUEST("ROLE_GUSET"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    UserRole(String value){
         this.value = value;
    }

    private String value;
}
