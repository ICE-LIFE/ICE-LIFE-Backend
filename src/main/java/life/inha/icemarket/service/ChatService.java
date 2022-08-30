package life.inha.icemarket.service;

import life.inha.icemarket.domain.Room;
import life.inha.icemarket.domain.User;
import life.inha.icemarket.exception.*;
import life.inha.icemarket.respository.ChatRepository;
import life.inha.icemarket.respository.RoomRepository;
import life.inha.icemarket.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ChatService {
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void createRoom(String roomName, List<Integer> memberIds) {
        List<User> members = userRepository.findAllById(memberIds);
        Room newRoom = Room.builder().name(roomName).users(members).build();
        newRoom = roomRepository.save(newRoom);

        for (User member : members) {
            messagingTemplate.convertAndSendToUser(member.getEmail(), "/queue/room/join", new JoinRoom(newRoom.getId(), memberIds));
        }
    }

    public void broadcastChat(Integer receivingRoomId, Integer senderId, String message) throws ForbiddenException, NotFoundException {
        Room receivingRoom = roomRepository.findById(receivingRoomId).orElseThrow(() -> new RoomNotFoundException(receivingRoomId));
        if (!userRepository.existsById(senderId)) {
            throw new UserNotFoundException(senderId);
        }

        List<User> receivers = receivingRoom.getUsers();
        if (receivers.stream().noneMatch(member -> member.getId().equals(senderId))) {
            throw new ForbiddenException(String.format("이용할 수 없는 채팅방입니다: room_id=%d, user_id=%d", receivingRoomId, senderId));
        }

        chatRepository.record(receivingRoomId, senderId, message);
        for (User receiver : receivers) {
            messagingTemplate.convertAndSendToUser(receiver.getEmail(), "/topic/room/" + receivingRoomId, new SendMessage(senderId, receivingRoomId, message));
        }
    }

    public void inviteUser(Integer roomId, Integer inviterId, Integer inviteeId) throws ForbiddenException, NotFoundException, ConflictException {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        User invitee = userRepository.findById(inviteeId).orElseThrow(() -> new UserNotFoundException(inviteeId));
        if (!userRepository.existsById(inviterId)) {
            throw new UserNotFoundException(inviterId);
        }

        List<User> members = room.getUsers();
        if (members.stream().noneMatch(member -> member.getId().equals(inviterId))) {
            throw new ForbiddenException(String.format("초대 권한이 없습니다: room_id=%d, user_id=%d", roomId, inviterId));
        }

        if (members.stream().anyMatch(member -> member.getId().equals(inviteeId))) {
            throw new ConflictException(String.format("이미 초대된 이용자입니다: room_id=%d, user_id=%d", roomId, inviteeId));
        }
        roomRepository.joinUser(roomId, inviteeId);

        members.add(invitee);
        for (User member : members) {
            messagingTemplate.convertAndSendToUser(member.getEmail(), "/queue/room/join", new JoinRoom(room.getId(), members.stream().map(User::getId).toList()));
        }
    }

    public void leaveRoom(Integer roomId, Integer memberId) throws NotFoundException, ConflictException {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException(roomId));
        if (!userRepository.existsById(memberId)) {
            throw new UserNotFoundException(memberId);
        }

        List<User> members = room.getUsers();
        if (members.stream().noneMatch(member -> member.getId().equals(memberId))) {
            throw new ConflictException(String.format("초대받지 못한 채팅방입니다: room_id=%d, user_id=%d", roomId, memberId));
        }
        roomRepository.leaveUser(roomId, memberId);
    }

    public record JoinRoom(Integer where, List<Integer> members) {
    }

    public record SendMessage(Integer from, Integer to, String content) { // from: sender id, to: room id
    }
}