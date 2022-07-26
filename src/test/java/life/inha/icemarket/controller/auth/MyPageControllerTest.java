package life.inha.icemarket.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.auth.User;
import life.inha.icemarket.dto.MyPageDto;
import life.inha.icemarket.service.auth.MyPageService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyPageController.class)
class MyPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    MyPageController myPageController;

    @Mock
    MyPageService myPageService;

    @BeforeEach
    public void init(){
        mvc = MockMvcBuilders.standaloneSetup(myPageController)
                .alwaysExpect(status().isOk())
                .build();
    }

    @DisplayName("마이페이지에서 유저 정보를 조회한다. ")
    @Test
    public void getUserInfo() throws Exception {
        // given
        User user = new User(12201863, "김민겸", "12201863@inha.edu", "겸이");
        MyPageDto myPageDto = new MyPageDto("김민겸", 12201863, "겸이");
        given(myPageService.getMyPageDto(anyInt())).willReturn(myPageDto);

        // when
        mvc.perform(get("/user/view")
                .param("userId", "12201863"))
                .andExpect(content().string(objectMapper.writeValueAsString(myPageDto)));
    }

    @DisplayName("마이페이지에서 유저 정보를 수정한다.")
    @Test
    public void updateUserInfo() throws Exception {
        // given
        User expectedUser = new User(12201863, "김민겸", "12201863@inha.edu", "겨미겨미");
        given(myPageService.update(anyInt(), anyString())).willReturn(expectedUser);

        // when
        String responseBody = mvc.perform(put("/user/mypage")
                .param("userId", "12201863")
                .param("nickname", "겨미겨미"))
                .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

        // then
        assertThat(responseBody).contains("겨미겨미");
    }
}