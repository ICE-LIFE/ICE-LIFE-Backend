package life.inha.icemarket.respository;

import life.inha.icemarket.domain.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {

}
