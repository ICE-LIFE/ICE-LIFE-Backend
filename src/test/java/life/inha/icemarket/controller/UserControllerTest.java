/*
    UserControllerTest.java 는 SignupController, FindPasswordController, WhoAmIController, EmailController를 테스트합니다.
    회원가입, 로그인, 비밀번호 찾기(초기화), 사용자 권한 테스트
 */

package life.inha.icemarket.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.service.EmailService;
import life.inha.icemarket.service.UserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSecurityService userSecurityService;

    @Autowired
    EmailService emailService;

    private static final String EMAIL = "daezang102@naver.com";

    private static String createToken(String email){
        if(email == null) email = EMAIL;
        Algorithm algorithm = Algorithm.HMAC256("ice-market");
        return JWT.create()
                .withIssuer("ice")
                .withSubject(email)
                .sign(algorithm);
    }

    @Test
    @Order(1)
    public void signup() throws Exception{
        MultiValueMap<String, String> SignupForm = new LinkedMultiValueMap<>();
        SignupForm.add("id","12340000");
        SignupForm.add("name", "TestUser");
        SignupForm.add("password1","password");
        SignupForm.add("password2","password");
        SignupForm.add("nickname","testuser");
        SignupForm.add("email",EMAIL);

        mvc.perform(post("/signup")
                            .params(SignupForm))
                        .andExpect(status().isOk())
                        .andExpect(content().string("success"));
        log.info("Signup Test End");
    }

    @Test
    @Order(2)
    public void LoginTest() throws Exception{
        String Email = EMAIL;
        String accessToken = createToken(Email);
        MultiValueMap<String, String> logininfo = new LinkedMultiValueMap<>();
        logininfo.add("email",Email);
        logininfo.add("password","password");
        mvc.perform(post("/login")
                .params(logininfo))
                        .andExpect(status().isOk())
                        .andExpect(content().string(accessToken));
        log.info("Login Test End");
    }

    @Test
    @Order(3)
    public void OnlyUserTest() throws Exception {
        mvc.perform(get("/onlyuser")
                        .header("authorization", "Bearer " + createToken(EMAIL)))
                .andExpect(status().is4xxClientError());
        log.info("OnlyUser Test End");
    }

    @Test
    @Order(4)
    public void OnlyAdminTest() throws Exception{
        mvc.perform(get("/onlyadmin")
                    .header("authorization", "Bearer " + createToken(EMAIL)))
                .andExpect(status().is4xxClientError());
        log.info("OnlyAdmin Test End");
    }

    @Test
    @Order(5)
    public void EmailConfirmTest() throws Exception{
        mvc.perform(get("/emailconfirm")
                .header("authorization", "Bearer " + createToken(EMAIL)))
                .andExpect(content().contentType("text/html;charset=UTF-8"));

        log.info("EmailConfirm GET TEST End");


        MultiValueMap<String, String> EmailInfo = new LinkedMultiValueMap<>();
        String ValidCode = emailService.loadEmailKey(EMAIL);
        EmailInfo.add("inputcode", ValidCode);
        EmailInfo.add("email",EMAIL);

        mvc.perform(post("/emailconfirm")
                        .params(EmailInfo)
                .header("authorization", "Bearer " + createToken(EMAIL)))
                .andExpect(content().string("[ROLE_USER]"));

        log.info("EmailConfirm POST TEST End");
    }

    @Test
    @Order(6)
    public void FindPwGetTest() throws Exception{
        mvc.perform(get("/findpw"))
                .andExpect(status().isOk());
        log.info("FindPwGet Test End");
    }

    @Test
    @Order(7)
    public void FindPwPostTest() throws Exception {
        MultiValueMap<String, String> findpwform = new LinkedMultiValueMap<>();
        findpwform.add("email",EMAIL);
        findpwform.add("nickname","testuser");
        mvc.perform(post("/findpw")
                        .params(findpwform))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"));

        log.info("FindPwpost Test End");
    }

    @Test
    @Order(8)
    public void ResetPwPostTest() throws Exception{
        MultiValueMap<String, String> resetpwform = new LinkedMultiValueMap<>();
        resetpwform.add("password1","123411");
        resetpwform.add("password2","123411");
        resetpwform.add("email", EMAIL);
        MvcResult result = mvc.perform(post("/resetpw")
                    .params(resetpwform))
                .andExpect(status().isOk())
                .andReturn();

        Optional<User> _User = userRepository.findByEmail(EMAIL);
        if (_User.isEmpty()) {
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }
        User user = _User.get();
        String result_string = result.getResponse().getContentAsString();
        assertThat(result_string)
                .isEqualTo(user.getPasswordHashed());
        
        log.info("ResetPwpost test End");
    }
}