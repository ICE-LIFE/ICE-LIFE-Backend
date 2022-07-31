package life.inha.icemarket.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Getter
@Entity(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String name;

    @NonNull
    private String image;

    @NonNull
    private Integer amount;

    @NonNull
    private Integer remainder;

    @Builder
    public Item(String name, String image, Integer amount) {
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.remainder = amount;
    }


    public void update(String name, String image, Integer amount, Integer remainder) {
        this.name = name;
        this.image = image;
        this.amount = amount;
        this.remainder = remainder;
    }

    public void updateRemainder(Integer remainder) {
        this.remainder = remainder;
    }
}
