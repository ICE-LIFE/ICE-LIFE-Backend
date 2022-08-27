package life.inha.icemarket.domain;

import life.inha.icemarket.dto.CommentSaveReqDto;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /* 게시글 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    /* 댓글 작성자 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User authorUser;

    /* 댓글 내용 */
    @NonNull
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    /* 생성일자 */
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdDate;

    protected Comment() {
    }

    public static Comment createComment(CommentSaveReqDto dto, Post post, User user) {
        Comment newComment = new Comment();
        newComment.post = post;
        newComment.authorUser = user;
        newComment.content = dto.getContent();
        newComment.createdDate = LocalDateTime.now();

        return newComment;
    }

}
