package life.inha.icemarket.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.nio.charset.StandardCharsets;
import java.util.List;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.domain.Post;
import life.inha.icemarket.dto.PostDto;
import life.inha.icemarket.dto.PostSaveDto;
import life.inha.icemarket.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "post", description = "게시판 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class PostController {
    private final PostService postService;

    /**
     * 게시글 등록 API
     * [POST] /board/{category}
     */
    @ApiDocumentResponse
    @PostMapping("/{category}")
    public ResponseEntity<String> savePost(@PathVariable String category, @RequestBody PostSaveDto postSaveDto) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(postService.save(category, postSaveDto), header, HttpStatus.OK);
    }

    /**
     * 게시글 목록 조회 API
     * [GET] /board/{category}
     */
    @ApiDocumentResponse
    @GetMapping("/{category}")
    public ResponseEntity<Page<Post>> getPosts(@PathVariable String category,
        @PageableDefault(size = 5, sort = "id") Pageable pageable) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(postService.getPosts(category, pageable), header, HttpStatus.OK);
    }

    /**
     * 게시글 단일 조회 API
     * [GET] /board/{category}/{id}
     */
    @ApiDocumentResponse
    @GetMapping("/{category}/{id}")
    public ResponseEntity<PostDto> getPosts(@PathVariable String category, @PathVariable Long id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(postService.getPost(id), header, HttpStatus.OK);
    }

    /**
     * 게시글 수정 API
     * [PATCH] /board/{category}/{id}
     */
    @ApiDocumentResponse
    @PatchMapping("/{category}/{id}")
    public ResponseEntity<String> modifyPost(@PathVariable String category, @PathVariable Long id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(postService.updatePost(category, id), header, HttpStatus.OK);
    }

    /**
     * 게시글 삭제 API
     * [DELETE] /board/{category}/{id}
     */
    @ApiDocumentResponse
    @DeleteMapping("/{category}/{id}")
    public ResponseEntity<String> deletePost(@PathVariable String category, @PathVariable Long id) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        return new ResponseEntity<>(postService.deletePost(category, id), header, HttpStatus.OK);
    }
}
