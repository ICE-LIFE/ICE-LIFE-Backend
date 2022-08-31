package life.inha.icemarket.dto;

import life.inha.icemarket.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CommentResDto {
    // 작성자 이름
    private String authorNickname;
    // 작성자 학번
    private Integer authorId;
    // 댓글 인덱스 번호
    private Integer commentIdx;
    // 댓글 내용
    private String comment;
    // 작성 일자
    private LocalDateTime createdDate;

    public static CommentResDto createCommentRes(Comment comment) {
        CommentResDto newCommentRes = new CommentResDto();
        newCommentRes.authorNickname = Objects.requireNonNullElse(comment.getAuthorUser().getNickname(), comment.getAuthorUser().getName());
        newCommentRes.authorId = comment.getAuthorUser().getId();
        newCommentRes.commentIdx = comment.getId();
        newCommentRes.comment = comment.getContent();
        newCommentRes.createdDate = comment.getCreatedDate();

        return newCommentRes;
    }
}
