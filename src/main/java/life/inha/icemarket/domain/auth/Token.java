package life.inha.icemarket.domain.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Token {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Request{
        private String id;
        private String secret;
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Response{
        private String token;
    }
}
