package life.inha.icemarket.respository;


import life.inha.icemarket.domain.Category;
import life.inha.icemarket.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findAllByCategory(Category category, Pageable pageable);

    List<Post> findByUserId(Integer userId);
}
