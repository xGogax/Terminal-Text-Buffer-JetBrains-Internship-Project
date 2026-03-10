package exception;

public class ScrollbackOutOfBoundsException extends TerminalBufferException{
    public ScrollbackOutOfBoundsException(int index, int col) {
        super("Scrollback position out of bounds: index="+index+", col="+col);
    }
}
