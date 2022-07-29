package life.inha.icemarket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Entity(name="item_history")
public class ItemHistrory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private Integer item_id;

    @NonNull
    private Integer manager_id;

    @NonNull
    private Timestamp rent_at;

    private Timestamp return_at;

    @Builder
    public ItemHistrory(Integer item_id, Integer manager_id, Timestamp rent_at){
        this.item_id = item_id;
        this.manager_id = manager_id;
        this.rent_at = rent_at;
    }

    public void update(Timestamp currentTime){
        this.return_at = currentTime;
    }
}
