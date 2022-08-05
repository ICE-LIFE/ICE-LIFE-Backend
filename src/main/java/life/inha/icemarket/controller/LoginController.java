package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.JwtTokenProvider;
import life.inha.icemarket.config.Token;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.config.UserAuthentication;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name="Login", description = "로그인 API")
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Operation(description = "로그인 페이지")
    @ApiDocumentResponse
    @ResponseBody
    @GetMapping("/login")
    public String login_get(){
        return "login_form";
    }

    @Operation(description = "로그인")
    @ApiDocumentResponse
    @RequestMapping(
            value = "/login",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(
            final HttpServletRequest req,
            final HttpServletResponse res,
            @Valid @RequestBody Token.Request request) throws Exception{

        System.out.println(request.getEmail());
        System.out.println(passwordEncoder.encode(request.getPassword()));

        User User = this.userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->new IllegalArgumentException("사용자가 없습니다.")
        );
        if(! passwordEncoder.matches(request.getPassword(), User.getPasswordHashed())){
            throw new IllegalArgumentException("비밀번호가 다릅니다.");
        }
        Authentication authentication = new UserAuthentication(request.getEmail(), null, null);
        String token = jwtTokenProvider.createToken(authentication);

        Token.Response response = Token.Response.builder()
                .token(token).
                build();
        log.info("Login @ {} with token {}",request.getEmail(), token);
        return ResponseEntity.ok(response);
    }
}
