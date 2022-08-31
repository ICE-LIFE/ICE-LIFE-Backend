package life.inha.icemarket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import life.inha.icemarket.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@AllArgsConstructor
public class PostDto {
    private Integer id;
    private Integer categoryId;
    private Integer authorId;
    private String authorNickname;
    private String title;
    private String content;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostDto getPost(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .categoryId(post.getCategory().getId())
                .authorId(post.getUser().getId())
                .authorNickname(Objects.requireNonNullElse(post.getUser().getNickname(), post.getUser().getName()))
                .title(post.getTitle())
                .content(post.getContent())
                .thumbnail(post.getThumbnail())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
