package life.inha.icemarket.dto;

import life.inha.icemarket.domain.core.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
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
