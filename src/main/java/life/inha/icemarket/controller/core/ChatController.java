package life.inha.icemarket.controller.core;

import life.inha.icemarket.domain.core.Room;
import life.inha.icemarket.domain.core.User;
import life.inha.icemarket.respository.ChatRepository;
import life.inha.icemarket.respository.RoomRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(RoomRepository roomRepository, ChatRepository chatRepository, SimpMessagingTemplate messagingTemplate) {
        this.roomRepository = roomRepository;
        this.chatRepository = chatRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room/{roomId}")
    @Transactional
    public void chat(@DestinationVariable int roomId, String message, Principal principal) {
        Room room = roomRepository.findById(roomId).orElseThrow();
        List<User> users = room.getUsers();

        String sourceUser = principal.getName();
        chatRepository.saveChat(roomId, Integer.parseInt(sourceUser), message);

        for (User targetUser : users) {
            messagingTemplate.convertAndSendToUser(String.valueOf(targetUser.getId()), "/queue/room/" + roomId, message);
        }
    }
}
