USE chess;

create table IF NOT EXISTS room
(
    room_id BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(64) NOT NULL,
    turn    VARCHAR(64) NOT NULL,
    PRIMARY KEY (room_id)
);

create table IF NOT EXISTS board
(
    board_id BIGINT      NOT NULL AUTO_INCREMENT,
    position VARCHAR(64) NOT NULL,
    piece_type VARCHAR(64) NOT NULL,
    room_id  BIGINT,
    PRIMARY KEY (board_id),
    FOREIGN KEY (room_id) references room (room_id)
);
