package chess.view;

public enum WinningResultView {

    BLACK_WIN("BLACK"),
    WHITE_WIN("WHITE"),
    TIE("무승부");

    private final String viewName;

    WinningResultView(final String viewName) {
        this.viewName = viewName;
    }

    public static String findViewName(String name) {
        return valueOf(name).viewName;
    }
}
