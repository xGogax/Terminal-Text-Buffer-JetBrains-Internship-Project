package terminal;

public enum StyleFlag {
    BOLD(1),
    ITALIC(3),
    UNDERLINE(4),
    STRIKETHROUGH(9);

    private final int code;

    StyleFlag(int code) { this.code = code; }
    public int getCode() { return code; }
}