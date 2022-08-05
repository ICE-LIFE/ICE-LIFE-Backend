package life.inha.icemarket.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostCommentReq {
    private Integer postIdx;
    private Integer authorIdx;
    private String content;

    @Builder
    public PostCommentReq(Integer postIdx, Integer authorIdx, String content){
        this.postIdx = postIdx;
        this.authorIdx = authorIdx;
        this.content = content;
    }
}
