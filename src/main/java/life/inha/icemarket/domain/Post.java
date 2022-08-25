package life.inha.icemarket.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue

    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Builder
    public Post(User user, String title){
        this.user=user;
        this.title=title;
    }

}
