package life.inha.icemarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCommentRes {
    private Integer commentIdx;

    public PostCommentRes(Integer commentIdx){

        this.commentIdx = commentIdx;
    }
}
