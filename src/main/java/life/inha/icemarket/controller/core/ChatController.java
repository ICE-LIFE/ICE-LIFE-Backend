package life.inha.icemarket.controller.core;

import life.inha.icemarket.domain.core.Room;
import life.inha.icemarket.domain.core.User;
import life.inha.icemarket.respository.ChatRepository;
import life.inha.icemarket.respository.RoomRepository;
import life.inha.icemarket.respository.UserRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Controller
public class ChatController {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(RoomRepository roomRepository, ChatRepository chatRepository, UserRepository userRepository, SimpMessagingTemplate messagingTemplate) {
        this.roomRepository = roomRepository;
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room/{roomId}")
    @Transactional
    public void chat(Principal principal, @DestinationVariable int roomId, String message) {
        int sourceUserId = Integer.parseInt(principal.getName());
        Room room = roomRepository.findById(roomId).orElseThrow(); // check no room exception
        if (room.getUsers().stream().noneMatch(user -> user.getId() == sourceUserId)) {
            // throw no user in room exception
            return;
        }
        List<User> users = room.getUsers();

        chatRepository.record(roomId, sourceUserId, message);

        for (User targetUser : users) {
            messagingTemplate.convertAndSendToUser(String.valueOf(targetUser.getId()), "/queue/room/" + roomId, message);
        }
    }

    @MessageMapping("/room/{roomId}/join")
    @Transactional
    public void join(Principal principal, @DestinationVariable int roomId) {
        int userId = Integer.parseInt(principal.getName());
        if (!roomRepository.existsById(roomId)) {
            // throw no room exception
            return;
        } else if (!userRepository.existsById(userId)) {
            // throw no user exception
            return;
        }
        roomRepository.join(roomId, userId);
    }

    @MessageMapping("/room/{roomId}/leave")
    @Transactional
    public void leave(Principal principal, @DestinationVariable int roomId) {
        int userId = Integer.parseInt(principal.getName());
        if (!userRepository.existsById(userId)) {
            // throw no user exception
            return;
        }
        Room room = roomRepository.findById(roomId).orElseThrow(); // check no room exception
        if (room.getUsers().stream().noneMatch(user -> user.getId() == userId)) {
            // throw no user in room exception
            return;
        }
        roomRepository.leave(roomId, userId);
    }
}
