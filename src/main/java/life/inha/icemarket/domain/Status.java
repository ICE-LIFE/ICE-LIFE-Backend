package life.inha.icemarket.domain;

public enum Status {
    JOIN, // 대기
    APPROVE, // 승인
    DENY, // 거절
    EXPEL // 승인 후 추방
}
