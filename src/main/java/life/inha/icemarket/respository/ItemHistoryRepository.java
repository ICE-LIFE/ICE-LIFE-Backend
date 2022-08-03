package life.inha.icemarket.respository;

import life.inha.icemarket.domain.ItemHistory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ItemHistoryRepository extends CrudRepository<ItemHistory, Integer> {
    @Query(value = "SELECT COUNT(return_at) FROM item_history WHERE item_id=?1", nativeQuery = true)
    Integer countReturnItem(Integer item_id);

    @Query(value = "SELECT COUNT(*) FROM item_history WHERE item_id=?1", nativeQuery = true)
    Integer countItemHistory(Integer item_id);
}
