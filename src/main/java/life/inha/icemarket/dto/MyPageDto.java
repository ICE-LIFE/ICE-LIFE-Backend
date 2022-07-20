package life.inha.icemarket.dto;

import life.inha.icemarket.domain.core.Post;
import lombok.Builder;

import java.util.List;

@Builder
public class MyPageDto {
    private String name;

    private Integer id;

    private String nickname;

    private List<Post> postList;
}
