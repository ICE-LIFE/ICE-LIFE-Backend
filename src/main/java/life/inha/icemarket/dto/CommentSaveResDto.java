package life.inha.icemarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentSaveResDto {
    private Integer commentIdx;

    public CommentSaveResDto(Integer commentIdx){

        this.commentIdx = commentIdx;
    }
}
