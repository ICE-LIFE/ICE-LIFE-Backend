package life.inha.icemarket.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.IceMarketApplication;
import life.inha.icemarket.config.JwtDecodeFilter;
import life.inha.icemarket.config.JwtLoginFilter;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.UserRepository;
import life.inha.icemarket.service.UserSecurityService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SignupControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserSecurityService userSecurityService;

    JwtLoginFilter jwtLoginFilter;


    private static String createToken(String email){
        if(email == null) email = "test@example.com";
        Algorithm algorithm = Algorithm.HMAC256("ice-market");
        return JWT.create()
                .withIssuer("ice")
                .withSubject(email)
                .sign(algorithm);
    }

    @Test
    @Order(1)
    public void signup() throws Exception {
        String content = this.objectMapper.writeValueAsString(
                new SignupController.UserCreateForm(
                        12340000,
                        "TestUser",
                        "password",
                        "password",
                        "testuser",
                        "test@example.com"
                )
        );

        mvc.perform(post("/signup")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));
        System.out.println("Signup Test End");
    }

    @Test
    @Order(2)
    public void LoginTest() throws Exception{
        String Email = "test@example.com";
        String accessToken = createToken(Email);
        MultiValueMap<String, String> logininfo = new LinkedMultiValueMap<>();
        logininfo.add("email",Email);
        logininfo.add("password","password");
        mvc.perform(post("/login")
                .params(logininfo))
                        .andExpect(status().isOk())
                        .andExpect(content().string(accessToken));
        System.out.println("Login Test End");
    }

    @Test
    @Order(3)
    public void OnlyUsersTest() throws Exception {
        mvc.perform(get("/onlyuser")
                        .header("authorization", "Bearer " + createToken(null)))
                .andExpect(status().is2xxSuccessful());
        System.out.println("OnlyUsers Test End");
    }

    @Test
    @Order(4)
    public void OnlyAdminTest() throws Exception{
        mvc.perform(get("/onlyadmin")
                    .header("authorization", "Bearer " + createToken(null)))
                .andExpect(status().is4xxClientError());
        System.out.println("OnlyAdmin Test End");
    }

    @Test
    @Order(5)
    public void FindPwGetTest() throws Exception{
        mvc.perform(get("/findpw"))
                .andExpect(status().isOk());
        System.out.println("FindPwGet Test End");
    }

    //todo : findpw post, resetpw post test
}