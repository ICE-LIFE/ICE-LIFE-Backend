package life.inha.icemarket.exception;

public class RoomNotFoundException extends NotFoundException {
    public RoomNotFoundException(Integer roomId) {
        super(String.format("존재하지 않는 채팅방입니다: room_id=%d", roomId));
    }
}
