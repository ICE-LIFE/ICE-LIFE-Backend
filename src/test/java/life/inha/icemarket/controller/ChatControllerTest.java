package life.inha.icemarket.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
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
    }

    @Test
    public void try_chat() throws ExecutionException, InterruptedException {
        WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
        headers.add("access_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0IiwiaXNzIjoiaWNlIiwiaWF0IjoxNTE2MjM5MDIyfQ.LnuHsnKQ_p_DJgvNoHQmnk_SoXS4a1sIqemSijBpbn0"); // from https://jwt.io/
        StompSession session = client.connect(String.format("http://localhost:%d/api/ws", port), headers, new StompSessionHandlerAdapter() {
        }).get();

        Integer userId = 1234;
        Integer roomId = 1;

        session.subscribe(String.format("/user/%d/topic/room/%d", userId, roomId), new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return String.class;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                assert payload == "Hello World";
            }
        });

        session.send("/room/create", "Test");
        session.send(String.format("/room/%d", roomId), "Hello World");
    }
}
