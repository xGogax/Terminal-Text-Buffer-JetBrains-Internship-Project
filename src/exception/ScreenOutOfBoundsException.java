package exception;

public class ScreenOutOfBoundsException extends TerminalBufferException{
    public ScreenOutOfBoundsException(int row, int col) {
        super("Screen position out of bounds: row="+row+", col="+col);
    }
}
