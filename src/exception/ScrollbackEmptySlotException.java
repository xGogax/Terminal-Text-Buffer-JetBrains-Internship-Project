package exception;

public class ScrollbackEmptySlotException extends TerminalBufferException {
    public ScrollbackEmptySlotException(int index) {
        super("Scrollback slot " + index + " is empty.");
    }
}
