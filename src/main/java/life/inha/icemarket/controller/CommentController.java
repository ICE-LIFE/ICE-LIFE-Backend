package life.inha.icemarket.controller;

import life.inha.icemarket.dto.GetCommentRes;
import life.inha.icemarket.dto.PostCommentReq;
import life.inha.icemarket.dto.PostCommentRes;
import life.inha.icemarket.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 생성 API
     * [Post] /comment
     * @return PostCommentRes
     */
    @PostMapping("/comment")
    public PostCommentRes createComment(@RequestBody PostCommentReq dto){
        return commentService.createComment(dto);
    }

    /**
     * 댓글 목록 보기 API
     * [GET] /comment/3
     * @return List<GetCommentRes>
     */
    @GetMapping("/comment/{id}")
    public List<GetCommentRes> readComment(@PathVariable Integer id){
        // id : 게시글 인덱스
        return commentService.readComment(id);
    }

    /**
     * 댓글 삭제 API
     * [Delete] /comment/{id}
     * @return
     */
    @DeleteMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable Integer id) {
        // id : 댓글 인덱스
        commentService.deleteComment(id);
        return "삭제 완료 !";
    }

}
