package life.inha.icemarket.controller.auth;

import life.inha.icemarket.config.JwtTokenProvider;
import life.inha.icemarket.config.Token;
import life.inha.icemarket.domain.auth.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginApiController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

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

        SiteUser siteUser = this.userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->new IllegalArgumentException("사용자가 없습니다.")
        );
        if(! passwordEncoder.matches(request.getPassword(), siteUser.getPasswordHashed())){
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
