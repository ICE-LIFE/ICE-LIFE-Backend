package life.inha.icemarket.domain;

import lombok.Getter;


@Getter
public enum UserRole {
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST");

    UserRole(String value){
         this.value = value;
    }

    private final String value;
}
