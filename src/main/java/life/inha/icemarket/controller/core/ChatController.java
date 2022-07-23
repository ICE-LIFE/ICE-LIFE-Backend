package life.inha.icemarket.controller.core;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/send")
    @SendTo("/topic/greetings")
    public String send(String message) {
        System.out.println(message);
        return "received";
    }
}
