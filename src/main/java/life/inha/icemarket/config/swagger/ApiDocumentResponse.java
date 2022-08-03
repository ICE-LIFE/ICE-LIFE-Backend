package life.inha.icemarket.config.swagger;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "API 호출 성공"),
        @ApiResponse(responseCode = "201", description = "API 호출 자원 생성 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 Method 요청"),
        @ApiResponse(responseCode = "401", description = "인증 실패"),
        @ApiResponse(responseCode = "403", description = "접근 권한 거부"),
        @ApiResponse(responseCode = "404", description = "존재하지 않는 API")})
public @interface ApiDocumentResponse {
}
