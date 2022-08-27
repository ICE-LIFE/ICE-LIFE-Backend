package life.inha.icemarket.config.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * SwaggerUi Api문서에 로그인 화면을 등록하기 위함입니다.
 */
@Tag(name = "Login", description = "로그인 API")
@Controller
public class LoginAPIDocument {
    @Operation(description = "로그인 페이지 - email과 비밀번호를 param으로 받아오면 jwt 토큰값을 리턴합니다.")
    @ApiDocumentResponse
    @RequestMapping(value = "/login ", method = RequestMethod.POST)
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password
    ) {
        return "JWT토큰";
    }
}
