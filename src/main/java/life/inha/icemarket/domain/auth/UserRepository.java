package life.inha.icemarket.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Integer> {
    SiteUser findById(int id);
    Optional<SiteUser> findByEmail(String email);
    Optional<SiteUser> findByNickname(String nickname);
}

//public interface UserRepository extends JpaRepository<User, Integer>{
//
//}