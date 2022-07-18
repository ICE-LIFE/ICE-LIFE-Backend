CREATE TABLE users
(
    id              INT PRIMARY KEY COMMENT '학번',
    name            VARCHAR(10)  NOT NULL COMMENT '이름',
    email           VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일',
    password_hashed CHAR(60)     NOT NULL COMMENT '암호화된 비밀번호',
    nickname        VARCHAR(10) UNIQUE COMMENT '별명',
    created_at      TIMESTAMP    NOT NULL COMMENT '가입일',
    deleted_at      TIMESTAMP    COMMENT '탈퇴일'
);
