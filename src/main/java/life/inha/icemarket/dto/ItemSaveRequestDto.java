package life.inha.icemarket.dto;


import life.inha.icemarket.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemSaveRequestDto {
    private String name;
    private String image;
    private Integer amount;

    @Builder
    public ItemSaveRequestDto(String name, String image, Integer amount) {
        this.name = name;
        this.image = image;
        this.amount = amount;
    }

    public Item toEntity() {
        return Item.builder()
                .name(name)
                .image(image)
                .amount(amount)
                .build();
    }
}
