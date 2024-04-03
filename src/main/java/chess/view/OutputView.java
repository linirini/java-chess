package chess.view;

import chess.domain.position.ChessFile;
import chess.domain.position.ChessRank;
import chess.dto.BoardStatus;
import chess.dto.GameStatus;
import chess.dto.PieceInfo;
import chess.dto.RoomInfos;

import java.util.Arrays;
import java.util.StringJoiner;

public class OutputView {

    private static final OutputView INSTANCE = new OutputView();
    private static final String EMPTY = ".";
    private static final String ERROR_PREFIX = "[ERROR] ";

    private OutputView() {
    }

    public static OutputView getInstance() {
        return INSTANCE;
    }

    public void printGameStartMessage() {
        StringJoiner startMessageJoiner = new StringJoiner(System.lineSeparator());
        startMessageJoiner.add("> 체스 게임을 시작합니다.");
        startMessageJoiner.add("> 게임 시작 : start");
        startMessageJoiner.add("> 게임 종료 : end");
        startMessageJoiner.add("> 게임 점수 및 승패 확인 : status");
        startMessageJoiner.add("> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
        System.out.println(startMessageJoiner);
    }

    public void printGameStatus(final GameStatus status) {
        System.out.println(String.format("WHITE 진영 : %f, BLACK 진영 : %f, 승자 : %s", status.whiteScore(), status.blackScore(), status.winningResult()));
    }

    public void printChessBoard(final BoardStatus status) {
        String[][] board = createInitBoard();
        applyBoardStatus(status, board);

        StringJoiner boardJoiner = new StringJoiner(System.lineSeparator());
        for (String[] line : board) {
            boardJoiner.add(createBoardLine(line));
        }
        System.out.println(boardJoiner + System.lineSeparator());
    }

    private String[][] createInitBoard() {
        String[][] board = new String[ChessRank.maxIndex() + 1][ChessFile.maxIndex() + 1];
        for (int rank = ChessRank.minIndex(); rank <= ChessRank.maxIndex(); rank++) {
            Arrays.fill(board[rank], EMPTY);
        }
        return board;
    }

    private void applyBoardStatus(final BoardStatus status, final String[][] board) {
        for (PieceInfo piecePositionInfo : status.piecePositionInfos()) {
            String name = PieceTypeView.findViewName(piecePositionInfo.pieceType());
            board[ChessRank.maxIndex() - piecePositionInfo.rankIndex()][piecePositionInfo.fileIndex()] = name;
        }
    }

    private StringBuilder createBoardLine(final String[] line) {
        StringBuilder lineBuilder = new StringBuilder();
        for (String point : line) {
            lineBuilder.append(point);
        }
        return lineBuilder;
    }

    public void printGameErrorMessage(final String message) {
        System.out.println(ERROR_PREFIX + message + System.lineSeparator());
    }

    public void printEndMessage() {
        StringJoiner endMessageJoiner = new StringJoiner(System.lineSeparator());
        endMessageJoiner.add("게임이 종료되었습니다.");
        endMessageJoiner.add("<게임 결과>");
        System.out.println(endMessageJoiner);
    }

    public void printGameMessage() {
        StringJoiner gameMessageJoiner = new StringJoiner(System.lineSeparator());
        gameMessageJoiner.add("<< 체스 게임 >>");
        gameMessageJoiner.add("> 게임 종료 : create 이름 - 예. create 리니게임방");
        gameMessageJoiner.add("> 게임 입장 : enter 이름 - 예. enter 리니게임방");
        System.out.println(gameMessageJoiner);
    }

    public void printRooms(final RoomInfos roomInfos) {
        StringJoiner roomJoiner = new StringJoiner(System.lineSeparator());
        roomJoiner.add("<< 입장 가능한 방 >>");
        for (final String name : roomInfos.names()) {
            roomJoiner.add(name);
        }
        System.out.println(roomJoiner + System.lineSeparator());
    }
}
