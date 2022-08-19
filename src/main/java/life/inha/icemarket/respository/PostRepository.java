package life.inha.icemarket.respository;

import life.inha.icemarket.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllById(Integer userId);
}
