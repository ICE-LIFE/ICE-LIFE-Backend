package life.inha.icemarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.Role;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.MyPageDto;
import life.inha.icemarket.service.auth.MyPageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(life.inha.icemarket.controller.auth.MyPageController.class)
class MyPageControllerTest {

    @InjectMocks
    life.inha.icemarket.controller.auth.MyPageController myPageController;
    @Mock
    MyPageService myPageService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void init() {
        mvc = MockMvcBuilders.standaloneSetup(myPageController)
                .alwaysExpect(status().isOk())
                .build();
    }

    @DisplayName("마이페이지에서 유저 정보를 조회한다. ")
    @Test
    public void getUserInfo() throws Exception {
        // given
        User user = new User(12201863, "김민겸", "12201863@inha.edu", "겸이", Role.ADMIN);
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
        User expectedUser = new User(12201863, "김민겸", "12201863@inha.edu", "겨미겨미", Role.ADMIN);
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