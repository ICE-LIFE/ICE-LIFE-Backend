package life.inha.icemarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.CommentSaveReqDto;
import life.inha.icemarket.respository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CommentRepository commentRepository;

    private static final String BASE_URL = "/comment";

    @Test
    void 댓글_저장() throws Exception {
        //given
        User user = new User(12192152, "허영은", "12192152@inha.edu", "푸하항");
        Integer postIdx = 1;
        Integer authorIdx = 12192152;
        String text = "안녀하셍요 댓글이에ㅇㅛㅇ";

        //when
        String body = mapper.writeValueAsString(
                CommentSaveReqDto.builder().postIdx(postIdx).content(text).build()
        );

        //then
        mvc.perform(post( BASE_URL)
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
