package life.inha.icemarket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import life.inha.icemarket.domain.Comment;
import life.inha.icemarket.domain.Post;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.CommentSaveReqDto;
import life.inha.icemarket.respository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CommentControllerTest {
    private static final String BASE_URL = "/comment";
    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    CommentRepository commentRepository;

    @Test
    @WithMockUser
    void 댓글_저장() throws Exception {
        //given
        CommentSaveReqDto dto = CommentSaveReqDto.builder().postIdx(1).content("안녀하셍요 댓글이에ㅇㅛㅇ").build();

        //when
        mvc.perform(post(BASE_URL)
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void 댓글_목록_조회() throws Exception {
        //given
        User user = new User(12192152, "허영은", "12192152@inha.edu", "푸하항");
        Post post = Post.builder().user(user).title("~ 제목 ~").build();
        CommentSaveReqDto comment1 = CommentSaveReqDto.builder().postIdx(Math.toIntExact(post.getId())).content("~ 댓글 1 ~").build();
        CommentSaveReqDto comment2 = CommentSaveReqDto.builder().postIdx(Math.toIntExact(post.getId())).content("~ 댓글 2 ~").build();

        commentRepository.save(Comment.createComment(comment1, post, user));
        commentRepository.save(Comment.createComment(comment2, post, user));

        //when
        mvc.perform(get(BASE_URL)
                        .param("board", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void 단일_댓글_조회() throws Exception {
        //given
        User user = new User(12192152, "허영은", "12192152@inha.edu", "푸하항");
        Post post = Post.builder().user(user).title("~ 제목 ~").build();
        CommentSaveReqDto dto = CommentSaveReqDto.builder().postIdx(Math.toIntExact(post.getId())).content("~ 댓글 ~").build();
        Comment comment = Comment.createComment(dto, post, user);
        commentRepository.save(Comment.createComment(dto, post, user));

        //when
        mvc.perform(get(BASE_URL + "/" + comment.getId()))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(commentRepository.findById(comment.getId())).contains(comment);

    }

    @Test
    void 댓글_삭제() throws Exception {
        //given
        User user = new User(12192152, "허영은", "12192152@inha.edu", "푸하항");
        Post post = Post.builder().user(user).title("~ 제목 ~").build();
        CommentSaveReqDto dto = CommentSaveReqDto.builder().postIdx(Math.toIntExact(post.getId())).content("~ 댓글 ~").build();
        Comment comment = Comment.createComment(dto, post, user);
        commentRepository.save(Comment.createComment(dto, post, user));

        //when
        mvc.perform(delete(BASE_URL))
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertThat(commentRepository.findById(comment.getId())).isEmpty();
    }
}
