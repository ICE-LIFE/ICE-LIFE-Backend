package life.inha.icemarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "posts")
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String content;

    private String thumbnail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Post(Integer id, String title, String content, String thumbnail, User user, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.thumbnail = thumbnail;
        this.user = user;
        this.category = category;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}