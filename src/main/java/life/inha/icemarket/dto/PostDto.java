package life.inha.icemarket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import life.inha.icemarket.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@AllArgsConstructor
public class PostDto {
    private Long id;
    private Long categoryId;
    private String title;
    private String content;
    private String thumbnail;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostDto getPost(Post post) {
        return PostDto.builder()
            .id(post.getId())
            .categoryId(post.getCategory().getId())
            .title(post.getTitle())
            .content(post.getContent())
            .thumbnail(post.getThumbnail())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getModifiedAt())
            .build();
    }
}
