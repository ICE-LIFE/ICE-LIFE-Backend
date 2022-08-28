package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.dto.CommentResDto;
import life.inha.icemarket.dto.CommentSaveReqDto;
import life.inha.icemarket.dto.CommentSaveResDto;
import life.inha.icemarket.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Tag(name = "comment", description = "댓글 API")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * 댓글 생성 API
     * [Post] /comment
     *
     * @return PostCommentRes
     */
    @PostMapping("/comment")
    public ResponseEntity<CommentSaveResDto> createComment(@AuthenticationPrincipal User user, @RequestBody @Valid CommentSaveReqDto dto) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(commentService.createComment(user, dto), header, HttpStatus.OK);
    }

    /**
     * 댓글 목록 보기 API
     * [GET] comment?board=3
     *
     * @return List<GetCommentRes>
     */
    @GetMapping("/comment")
    public ResponseEntity<List<CommentResDto>> readCommentList(@RequestParam Integer board, @PageableDefault(direction = Sort.Direction.DESC, size = 5) Pageable pageable) {
        // board : 게시글
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(commentService.readCommentList(board, pageable), header, HttpStatus.OK);
    }

    /**
     * 단일 댓글 보기 API
     * [GET] /comment/3
     *
     * @return GetCommentRes
     */
    @GetMapping("/comment/{id}")
    public ResponseEntity<CommentResDto> readComment(@PathVariable Integer id) {
        // id : 댓글 인덱스
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(commentService.readComment(id), header, HttpStatus.OK);
    }


    /**
     * 댓글 삭제 API
     * [Delete] /comment/{id}
     *
     * @return
     */
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Integer id) {
        // id : 댓글 인덱스
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        commentService.deleteComment(id);
        return new ResponseEntity<>("삭제 완료 !", header, HttpStatus.OK);
    }

}
