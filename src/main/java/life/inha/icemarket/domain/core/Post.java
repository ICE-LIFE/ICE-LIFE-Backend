package life.inha.icemarket.domain.core;

import life.inha.icemarket.domain.auth.User;

import javax.persistence.*;

@Entity
public class Post {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
