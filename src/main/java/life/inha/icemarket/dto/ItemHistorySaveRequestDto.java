package life.inha.icemarket.dto;

import life.inha.icemarket.domain.ItemHistory;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class ItemHistorySaveRequestDto {
    private Integer itemId;
    private Integer managerId;
    private Timestamp rentAt;

    @Builder
    public ItemHistorySaveRequestDto(Integer itemId, Integer managerId) {
        this.itemId = itemId;
        this.managerId = managerId;
        this.rentAt = new Timestamp(System.currentTimeMillis());
    }

    public void setRentAt() {
        this.rentAt = new Timestamp(System.currentTimeMillis());
    }

    public ItemHistory toEntity() {
        return ItemHistory.builder()
                .itemId(itemId)
                .managerId(managerId)
                .rentAt(rentAt)
                .build();
    }
}
