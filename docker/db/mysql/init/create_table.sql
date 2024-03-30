/*create table user(
    user_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (user_id)
);*/

create table room(
    room_id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(64) NOT NULL,
    turn VARCHAR(64) NOT NULL,
    user_id BIGINT,
    PRIMARY KEY (room_id)
    /*FOREIGN KEY (user_id) references user (user_id)*/
);

create table board(
    board_id BIGINT NOT NULL AUTO_INCREMENT,
    position VARCHAR(64) NOT NULL,
    piece_id BIGINT,
    room_id BIGINT,
    PRIMARY KEY (board_id),
    FOREIGN KEY (room_id) references room (room_id),
    FOREIGN KEY (piece_id) references piece (piece_id)
);

create table piece(
    piece_id BIGINT NOT NULL AUTO_INCREMENT,
    type VARCHAR(64) NOT NULL,
    color VARCHAR(64) NOT NULL,
    PRIMARY KEY (piece_id)
);
