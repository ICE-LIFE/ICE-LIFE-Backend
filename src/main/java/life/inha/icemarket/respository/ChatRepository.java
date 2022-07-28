package life.inha.icemarket.respository;

import life.inha.icemarket.domain.Chat;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatRepository extends CrudRepository<Chat, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO room_chats (room_id, user_id, content) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void record(Integer roomId, Integer userId, String content);
}
