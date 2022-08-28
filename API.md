# API

## Web Socket

### Connect

```js
const client = new StompJs.Client({
    connectHeaders: { user: '12192021' },
    webSocketFactory() {
        return new SockJS("http://localhost:8080/ws");
    },
});
```

### /app/room/create

채팅방 생성

```js
client.subscribe(`/user/${email}/queue/room/join`, (message) => {
    console.log(message.body); // JSON
});

client.publish({
    destination: '/app/room/create',
    body: '테스트방,12190000,12200000',
});
```

출력 예시:

```json
{
    "where": 1, // 채팅방 ID
    "members": [12190000, 12200000] // 참가자 학번
}
```

### /app/room/{roomId}

채팅 전송

```js
client.subscribe(`/user/${email}/topic/room/1`, (message) => {
    console.log(message.body); // JSON
});

client.publish({
    destination: '/app/room/1',
    body: '안녕하세요',
});
```

출력 예시:

```json
{
    "from": 12190000, // 전송자 학번
    "to": 1, // 채팅방 ID
    "content": "안녕하세요"
}
```

### /app/room/{roomId}/invite

채팅방 초대

```js
client.publish({
    destination: '/app/room/1/invite',
    body: '12190000',
});
```

### /app/room/{roomId}/leave

채팅방 퇴장

```js
client.publish({ destination: '/app/room/1/leave' });
```
