package life.inha.icemarket.dto;

import life.inha.icemarket.domain.Comment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentResDto {
    // 작성자 이름
    private String author;
    // 댓글 인덱스 번호
    private Integer commentIdx;
    // 댓글 내용
    private String comment;
    // 작성 일자
    private LocalDateTime createdDate;

    public static CommentResDto createCommentRes(Comment comment){
        CommentResDto newCommentRes = new CommentResDto();
        newCommentRes.author = comment.getAuthorUser().getName();
        newCommentRes.commentIdx = comment.getId();
        newCommentRes.comment = comment.getContent();
        newCommentRes.createdDate = comment.getCreatedDate();

        return newCommentRes;
    }
}
