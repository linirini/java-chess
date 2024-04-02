USE chess;

create table IF NOT EXISTS room
(
    room_id BIGINT      NOT NULL AUTO_INCREMENT,
    name    VARCHAR(64) NOT NULL,
    turn    VARCHAR(64) NOT NULL,
    PRIMARY KEY (room_id)
);

create table IF NOT EXISTS piece
(
    piece_id BIGINT      NOT NULL AUTO_INCREMENT,
    type     VARCHAR(64) NOT NULL,
    color    VARCHAR(64) NOT NULL,
    PRIMARY KEY (piece_id)
);

create table IF NOT EXISTS board
(
    board_id BIGINT      NOT NULL AUTO_INCREMENT,
    position VARCHAR(64) NOT NULL,
    piece_id BIGINT,
    room_id  BIGINT,
    PRIMARY KEY (board_id),
    FOREIGN KEY (room_id) references room (room_id),
    FOREIGN KEY (piece_id) references piece (piece_id)
);

INSERT IGNORE INTO piece (type, color)
VALUES ('BISHOP', 'WHITE'),
       ('KING', 'WHITE'),
       ('KNIGHT', 'WHITE'),
       ('QUEEN', 'WHITE'),
       ('ROOK', 'WHITE'),
       ('PAWN', 'WHITE'),
       ('BISHOP', 'BLACK'),
       ('KING', 'BLACK'),
       ('KNIGHT', 'BLACK'),
       ('QUEEN', 'BLACK'),
       ('ROOK', 'BLACK'),
       ('PAWN', 'BLACK');
