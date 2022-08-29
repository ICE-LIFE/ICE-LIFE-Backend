package life.inha.icemarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.domain.UserRole;
import life.inha.icemarket.dto.UserListDto;
import life.inha.icemarket.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    @DisplayName("가입한 사용자 리스트를 조회한다.")
    @Test
    void getUserList() throws Exception {
        // given
        List<UserListDto> userListDto = new ArrayList<>();
        userListDto.add(new UserListDto(12201863, "김민겸", "mingyum119@naver.com", "겨미", LocalDateTime.now(), UserRole.GUEST));
        userListDto.add(new UserListDto(12221234, "홍길동", "abc123@naver.com", "길동이", LocalDateTime.now(), UserRole.GUEST));

        given(adminService.getUserList()).willReturn(userListDto);


        // when
        String responseBody = mvc.perform(get("/userList")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        // then
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(userListDto));
    }

    @Test
    void allowUser() throws Exception {
        // given
        User user = new User(12201863, "김민겸", "mingyum119@naver.com", "겨미");
        given(adminService.convertGuestToUser(anyInt())).willReturn(user.getId());

        // when
        mvc.perform(get("/userList/allowUser")
                        .param("userId", "12201863"))
                .andExpect(content().string("12201863"));

    }

    @Test
    void rejectUser() throws Exception {
        // given
        User user = new User(12201863, "김민겸", "mingyum119@naver.com", "겨미");
        given(adminService.convertGuestToUser(anyInt())).willReturn(user.getId());

        // when
        mvc.perform(get("/userList/rejectUser")
                        .param("userId", "12201863"))
                .andExpect(content().string("12201863"));
    }

    @Test
    void grantAdmin() throws Exception {
        User user = new User(12201863, "김민겸", "mingyum119@naver.com", "겨미");
        given(adminService.convertGuestToUser(anyInt())).willReturn(user.getId());

        // when
        mvc.perform(get("/userList/grantAdmin")
                        .param("userId", "12201863"))
                .andExpect(content().string("12201863"));
    }

    @Test
    void depriveAdmin() throws Exception {
        User user = new User(12201863, "김민겸", "mingyum119@naver.com", "겨미");
        given(adminService.convertGuestToUser(anyInt())).willReturn(user.getId());

        // when
        mvc.perform(get("/userList/depriveAdmin")
                        .param("userId", "12201863"))
                .andExpect(content().string("12201863"));
    }
}