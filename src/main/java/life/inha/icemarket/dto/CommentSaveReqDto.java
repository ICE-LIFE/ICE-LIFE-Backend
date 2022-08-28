package life.inha.icemarket.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CommentSaveReqDto {

    private Integer postIdx;
    @NotBlank(message = "댓글 내용을 입력해주세요")
    private String content;

    @Builder
    public CommentSaveReqDto(Integer postIdx, String content) {
        this.postIdx = postIdx;
        this.content = content;
    }
}
