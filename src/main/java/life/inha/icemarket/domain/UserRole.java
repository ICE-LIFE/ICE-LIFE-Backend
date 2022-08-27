package life.inha.icemarket.domain;

import lombok.Getter;


@Getter
public enum UserRole {
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }
}
