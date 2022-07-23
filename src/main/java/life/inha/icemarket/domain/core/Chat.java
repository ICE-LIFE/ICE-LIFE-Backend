package life.inha.icemarket.domain.core;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.Instant;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "room_chats")
public class Chat {
    @Id
    private int id;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NonNull
    private String content;

    @NonNull
    @CreatedDate
    private Instant createdAt;
}
