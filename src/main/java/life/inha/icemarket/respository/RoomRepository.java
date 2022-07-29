package life.inha.icemarket.respository;

import life.inha.icemarket.domain.Room;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO room_users (room_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    void joinUser(Integer roomId, Integer userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM room_users WHERE (room_id, user_id) = (?1, ?2)", nativeQuery = true)
    void leaveUser(Integer roomId, Integer userId);
}
