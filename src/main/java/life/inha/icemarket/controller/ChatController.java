package life.inha.icemarket.controller;


import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import life.inha.icemarket.config.swagger.ApiDocumentResponse;
import life.inha.icemarket.exception.BadRequestException;
import life.inha.icemarket.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;


@Tag(name = "chat", description = "채팅 API")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/room/create")
    @ApiDocumentResponse
    public void createRoom(Principal principal, String message) {
        try {
            Integer inviterId = Integer.valueOf(principal.getName());
            String[] args = message.split(",");
            if (args.length == 0) {
                throw new IllegalArgumentException();
            }
            String roomName = args[0];
            ArrayList<Integer> memberIds = new ArrayList<>();
            memberIds.add(inviterId);
            if (args.length >= 2) {
                for (int i = 1; i < args.length; i += 1) {
                    memberIds.add(Integer.valueOf(args[i]));
                }
            }
            chatService.createRoom(roomName, memberIds);
        } catch (NumberFormatException exception) {
            throw new BadRequestException(exception);
        }
    }

    @MessageMapping("/room/{roomId}")
    @ApiDocumentResponse
    public void broadcastChat(Principal principal, @DestinationVariable Integer roomId, String message) {
        try {
            Integer senderId = Integer.valueOf(principal.getName());
            chatService.broadcastChat(roomId, senderId, message);
        } catch (NumberFormatException exception) {
            throw new BadRequestException(exception);
        }
    }

    @MessageMapping("/room/{roomId}/invite")
    @ApiDocumentResponse
    public void inviteUser(Principal principal, @DestinationVariable Integer roomId, String message) {
        try {
            Integer inviterId = Integer.valueOf(principal.getName());
            Integer inviteeId = Integer.valueOf(message);
            chatService.inviteUser(roomId, inviterId, inviteeId);
        } catch (NumberFormatException exception) {
            System.out.println("error nfe");
            throw new BadRequestException(exception);
        }
    }

    @MessageMapping("/room/{roomId}/leave")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "잘못된 채팅방 퇴장 요청"),
    })
    public void leaveRoom(Principal principal, @DestinationVariable Integer roomId) throws IllegalArgumentException {
        try {
            Integer memberId = Integer.valueOf(principal.getName());
            chatService.leaveRoom(roomId, memberId);
        } catch (NumberFormatException exception) {
            throw new BadRequestException(exception);
        }
    }
}
