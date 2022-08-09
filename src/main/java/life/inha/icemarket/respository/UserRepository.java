package life.inha.icemarket.respository;

import life.inha.icemarket.dto.UserListDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import life.inha.icemarket.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, UserRepositoryCustom {
    Optional <User> findByEmail(String email);


}
