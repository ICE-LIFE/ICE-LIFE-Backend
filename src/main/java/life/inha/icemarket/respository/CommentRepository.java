package life.inha.icemarket.respository;

import life.inha.icemarket.domain.Comment;
import life.inha.icemarket.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {

    List<Comment> getCommentsByAuthorUser(Integer userId);
    List<Comment> findByPost(Post post, Pageable pageable);
}
