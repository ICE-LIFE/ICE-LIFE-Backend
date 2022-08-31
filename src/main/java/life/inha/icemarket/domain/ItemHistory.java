package life.inha.icemarket.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "item_history")
public class ItemHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private Integer itemId;

    @NonNull
    private Integer managerId;

    @NonNull
    private Timestamp rentAt;

    private Timestamp returnAt;

    @Builder
    public ItemHistory(Integer itemId, Integer managerId, Timestamp rentAt) {
        this.itemId = itemId;
        this.managerId = managerId;
        this.rentAt = rentAt;
    }

    public void update(Timestamp currentTime) {
        this.returnAt = currentTime;
    }
}
