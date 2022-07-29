package life.inha.icemarket.dto;

import life.inha.icemarket.domain.ItemHistrory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ItemHistorySaveRequestDto {
    private Integer item_id;
    private Integer manager_id;
    private Timestamp rent_at;
    //private Timestamp return_at;

    @Builder
    public ItemHistorySaveRequestDto(Integer item_id, Integer manager_id) {
        this.item_id = item_id;
        this.manager_id = manager_id;
        this.rent_at = new Timestamp(System.currentTimeMillis());
    }

    public void setRent_at() {
        this.rent_at = new Timestamp(System.currentTimeMillis());
    }

    public ItemHistrory toEntity(){
        return ItemHistrory.builder()
                .item_id(item_id)
                .manager_id(manager_id)
                .rent_at(rent_at)
                .build();
    }
}
