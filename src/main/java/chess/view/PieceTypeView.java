package chess.view;

public enum PieceTypeView {

    WHITE_PAWN("p"),
    BLACK_PAWN("P"),
    WHITE_ROOK("r"),
    BLACK_ROOK("R"),
    WHITE_KNIGHT("n"),
    BLACK_KNIGHT("N"),
    WHITE_BISHOP("b"),
    BLACK_BISHOP("B"),
    WHITE_KING("k"),
    BLACK_KING("K"),
    WHITE_QUEEN("q"),
    BLACK_QUEEN("Q");

    private final String viewName;

    PieceTypeView(final String viewName) {
        this.viewName = viewName;
    }

    public static String findViewName(String name) {
        return valueOf(name).viewName;
    }
}
