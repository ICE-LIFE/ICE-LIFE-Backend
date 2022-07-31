package life.inha.icemarket.controller;


import life.inha.icemarket.domain.Room;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.respository.ChatRepository;
import life.inha.icemarket.respository.RoomRepository;
import life.inha.icemarket.respository.UserRepository;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

    @MessageMapping("/room/create")
    @Transactional
    public void createRoom(Principal principal, String message) {

        Integer userId = Integer.valueOf(principal.getName());

        if (!userRepository.existsById(userId)) {
            // throw no user exception
            return;
        }
        String[] args = message.split(",");
        if (args.length == 0) {
            // throw no arg exception
            return;
        }
        String roomName = args[0];
        ArrayList<User> targetUsers = new ArrayList<>();
        if (args.length >= 2) {
            for (int i = 1; i < args.length; i += 1) {

                Integer targetUserId = Integer.valueOf(args[i]);

                User targetUser = userRepository.findById(targetUserId).orElseThrow(); // check no user exception
                targetUsers.add(targetUser);
            }
        }
        Room room = Room.builder()
                .name(roomName)
                .users(targetUsers)
                .build();
        Room createdRoom = roomRepository.save(room);
        System.out.println("create: " + createdRoom.getId());
    }

    @MessageMapping("/room/{roomId}")
    @Transactional

    public void chat(Principal principal, @DestinationVariable Integer roomId, String message) {
        Integer sourceUserId = Integer.valueOf(principal.getName());
        Room room = roomRepository.findById(roomId).orElseThrow(); // check no room exception
        if (room.getUsers().stream().noneMatch(user -> Objects.equals(user.getId(), sourceUserId))) {

            // throw no user in room exception
            return;
        }
        List<User> users = room.getUsers();

        chatRepository.record(roomId, sourceUserId, message);

        for (User targetUser : users) {
            messagingTemplate.convertAndSendToUser(String.valueOf(targetUser.getId()), "/topic/room/" + roomId, message);
        }
    }

    @MessageMapping("/room/{roomId}/invite")
    @Transactional

    public void inviteUser(Principal principal, @DestinationVariable Integer roomId, String message) {
        Integer userId = Integer.valueOf(principal.getName());

        if (!roomRepository.existsById(roomId)) {
            // throw no room exception
            return;
        } else if (!userRepository.existsById(userId)) {
            // throw no user exception
            return;
        }

        Integer targetUserId = Integer.valueOf(message);

        if (!userRepository.existsById(targetUserId)) {
            // throw no user exception
            return;
        }
        roomRepository.joinUser(roomId, targetUserId); // check user is already in room
    }

    @MessageMapping("/room/{roomId}/leave")
    @Transactional

    public void leaveRoom(Principal principal, @DestinationVariable Integer roomId) {
        Integer userId = Integer.valueOf(principal.getName());

        if (!userRepository.existsById(userId)) {
            // throw no user exception
            return;
        }
        Room room = roomRepository.findById(roomId).orElseThrow(); // check no room exception

        if (room.getUsers().stream().noneMatch(user -> Objects.equals(user.getId(), userId))) {

            // throw no user in room exception
            return;
        }
        roomRepository.leaveUser(roomId, userId);
    }
}
