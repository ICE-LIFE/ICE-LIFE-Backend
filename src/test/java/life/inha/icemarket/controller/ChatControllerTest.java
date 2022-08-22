package life.inha.icemarket.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.List;
import java.util.concurrent.ExecutionException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {
    @LocalServerPort
    private Integer port;

    private WebSocketStompClient client;

    @BeforeEach
    public void setup() {
        client = new WebSocketStompClient(new SockJsClient(List.of(new WebSocketTransport(new StandardWebSocketClient()))));
        client.setMessageConverter(new StringMessageConverter());
        client.setMessageConverter(new MappingJackson2MessageConverter());
    }

    @Test
    public void try_chat() throws ExecutionException, InterruptedException {
        String accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0IiwiaXNzIjoiaWNlIiwiaWF0IjoxNTE2MjM5MDIyfQ.LnuHsnKQ_p_DJgvNoHQmnk_SoXS4a1sIqemSijBpbn0";

        StompHeaders headers = new StompHeaders();
        headers.add("access_token", accessToken); // from https://jwt.io/
        StompSession session = client.connect(String.format("http://localhost:%d/api/ws", port), new WebSocketHttpHeaders(), headers, new StompSessionHandlerAdapter() {
        }).get();
        assert session.isConnected();

        Integer userId = 1234;
        Integer roomId = 1;

        session.send("/app/room/create", "Test");
        session.send(String.format("/app/room/%d/invite", roomId), String.valueOf(userId));
        session.send(String.format("/app/room/%d", roomId), "Hello World");
        session.send(String.format("/app/room/%d/leave", roomId), "");
        Thread.sleep(1000);
    }
}
