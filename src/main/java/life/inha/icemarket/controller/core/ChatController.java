package life.inha.icemarket.controller.core;

import life.inha.icemarket.domain.core.Room;
import life.inha.icemarket.respository.ChatRepository;
import life.inha.icemarket.respository.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class ChatController {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;

    public ChatController(RoomRepository roomRepository, ChatRepository chatRepository) {
        this.roomRepository = roomRepository;
        this.chatRepository = chatRepository;
    }

    @MessageMapping("/room/{roomId}")
    @SendToUser("/queue/room")
    public String chat(@DestinationVariable int roomId, String message) {
        System.out.println(message);

        Optional<Room> room = roomRepository.findById(roomId);
        chatRepository.saveChat(roomId, 1, message);

        return "received on " + roomId;
    }
}
