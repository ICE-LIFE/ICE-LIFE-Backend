package life.inha.icemarket.exception;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Integer userId) {
        super(String.format("존재하지 않는 이용자입니다: user_id=%d", userId));
    }
}
