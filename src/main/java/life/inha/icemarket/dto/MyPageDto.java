package life.inha.icemarket.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import life.inha.icemarket.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MyPageDto {
    private String name;

    private Integer id;

    private String nickname;

    private List<Post> postList;

    public MyPageDto(String name, Integer id, String nickname) {
        this.name = name;
        this.id = id;
        this.nickname = nickname;
    }
}
