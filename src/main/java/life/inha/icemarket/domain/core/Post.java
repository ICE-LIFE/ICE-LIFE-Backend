package life.inha.icemarket.domain.core;

import life.inha.icemarket.domain.auth.User;
import lombok.Getter;

import javax.persistence.*;

@Entity(name = "posts")
@Getter
public class Post {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
}
