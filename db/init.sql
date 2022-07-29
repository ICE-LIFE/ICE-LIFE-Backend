CREATE TABLE users (
    id              INT          NOT NULL PRIMARY KEY,
    name            VARCHAR(10)  NOT NULL,
    email           VARCHAR(100) NOT NULL UNIQUE,
    password_hashed CHAR(60)     NOT NULL,
    nickname        VARCHAR(10)  UNIQUE,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at      TIMESTAMP
);

CREATE TABLE categories (
    id   INT         NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE posts (
    id          BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    category_id INT       NOT NULL,
    author_id   INT       NOT NULL,
    content     TEXT      NOT NULL,
    thumbnail   VARCHAR(100),
    created_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at  TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
);

CREATE TABLE rooms (
    id         BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(10) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE room_chats (
    id         BIGINT    NOT NULL AUTO_INCREMENT PRIMARY KEY,
    room_id    BIGINT    NOT NULL,
    user_id    INT       NOT NULL,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (room_id) REFERENCES rooms (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE room_users (
    room_id BIGINT NOT NULL,
    user_id INT    NOT NULL,
    PRIMARY KEY (room_id, user_id),
    FOREIGN KEY (room_id) REFERENCES rooms (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE items (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(10)  NOT NULL,
    image           VARCHAR(100) NOT NULL,
    amount          INT          NOT NULL,
    remainder       INT          NOT NULL
);

CREATE TABLE item_history (
    id              INT          NOT NULL AUTO_INCREMENT PRIMARY KEY,
    item_id         INT          NOT NULL,
    manager_id      INT          NOT NULL,
    rent_at         TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    return_at       TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);