package life.inha.icemarket.domain.core;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.Instant;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "rooms")
public class Room {
    @Id
    private int id;

    @NonNull
    private String name;

    @NonNull
    @CreatedDate
    private Instant createdAt;

    @NonNull
    @LastModifiedDate
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;
}
