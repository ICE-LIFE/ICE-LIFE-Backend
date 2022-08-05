package life.inha.icemarket.domain;

import life.inha.icemarket.dto.PostCommentReq;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity(name = "comments")
public class Comment {

    @Id @GeneratedValue
    private Integer id;

    /* 게시글 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    /* 댓글 작성자 */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "author_id")
    private User authorUser;

    /* 댓글 내용 */
    @NonNull
    @Column(columnDefinition = "TEXT")
    private String content;

    /* 생성일자 */
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdDate;

    /* 삭제일자 */
    @LastModifiedDate
    @Column(name = "deleted_at")
    private LocalDateTime deletedDate;

    protected Comment() {};

    public static Comment createComment(PostCommentReq dto
            //, Post post
            , User user){
        Comment newComment = new Comment();
//        newComment.post = post;
        newComment.authorUser = user;
        newComment.content = dto.getContent();
        newComment.createdDate = LocalDateTime.now();

        return newComment;
    }

    /* 댓글 내용 수정 method */
    public void updateComment(String content) {
        this.content = content;
    }

}
