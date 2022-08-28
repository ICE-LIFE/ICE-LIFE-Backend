package life.inha.icemarket.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "categories")
@Getter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @Builder
    public Category(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
