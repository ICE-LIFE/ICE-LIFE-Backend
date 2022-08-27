package life.inha.icemarket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import life.inha.icemarket.domain.Category;
import life.inha.icemarket.domain.Post;
import life.inha.icemarket.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class PostSaveDto {
    private String title;

    private String content;

    private String thumbnail;

    public PostSaveDto(String  title, String content, String thumbnail) {
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
    }

    public Post toEntity(Category category, User user) {
        return Post.builder()
            .title(title)
            .content(content)
            .thumbnail(thumbnail)
            .user(user)
            .category(category)
            .build();
    }

}
