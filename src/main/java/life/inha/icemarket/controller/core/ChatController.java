package life.inha.icemarket.controller.core;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/room/{roomId}")
    @SendToUser("/queue/room")
    public String chat(@DestinationVariable int roomId, String message) {
        System.out.println(message);
        return "received on " + roomId;
    }
}
