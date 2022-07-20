package life.inha.icemarket.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.auth.User;
import life.inha.icemarket.dto.MyPageDto;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MyPageController.class)
class MyPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MyPageController myPageController;

    @DisplayName("마이페이지에서 유저 정보를 조회한다. ")
    @Test
    public void getUserInfo() throws Exception {
        // given
        User user = new User(12201863, "김민겸", "12201863@inha.edu", "겸이");
        MyPageDto myPageDto = new MyPageDto("김민겸", 12201863, "겸이");

        // when
        mvc.perform(get("/user/mypage")
//                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "12201863"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(myPageDto)));
    }

    @DisplayName("마이페이지에서 유저 정보를 수정한다.")
    @Test
    public void updateUserInfo() throws Exception {
        // given
        User user = new User(12201863, "김민겸", "12201863@inha.edu", "겸이");

        // when
        String responseBody = mvc.perform(put("/user/mypage")
                .param("userId", "12201863")
                .param("nickname", "겨미겨미"))
                .andExpect(status().isOk())
                        .andReturn().getResponse().toString();

        // then
        assertThat(responseBody).contains("겨미겨미");
    }
}