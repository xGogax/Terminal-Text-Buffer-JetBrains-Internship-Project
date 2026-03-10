package terminal;

import exception.ScreenOutOfBoundsException;
import exception.ScrollbackEmptySlotException;
import exception.ScrollbackOutOfBoundsException;

public class TerminalBuffer {
    private int width;
    private int height;
    private int scrollbackMaxSize;

    private Line[] screen;
    private Line[] scrollback;

    private int scrollbackStart = 0;
    private int scrollbackSize = 0;

    private final Cursor cursor;

    private Color currentForeground = Color.WHITE;
    private Color currentBackground = Color.BLACK;
    private StyleFlag[] currentStyles = new StyleFlag[0];

    public TerminalBuffer(int width, int height, int scrollbackMaxSize){
        this.width = width;
        this.height = height;
        this.scrollbackMaxSize = scrollbackMaxSize;

        this.screen = new Line[height];
        this.scrollback = new Line[scrollbackMaxSize];

        // Initialize empty screen
        for(int i = 0; i < height; i++){
            screen[i] = new Line(width);
        }

        this.cursor = new Cursor(0, 0);
    }

    private void addToScrollBack(Line line){
        if (scrollbackMaxSize == 0) return;

        int index = (scrollbackStart + scrollbackSize) % scrollbackMaxSize;
        scrollback[index] = line;

        if (scrollbackSize < scrollbackMaxSize) {
            scrollbackSize++;
        } else {
            scrollbackStart = (scrollbackStart + 1) % scrollbackMaxSize;
        }
    }

    public Cell getAttributesAtPositionScreen(int row, int col) {
        if (row < 0 || row >= height || col < 0 || col >= width) {
            throw new ScreenOutOfBoundsException(row, col);
        }

        return screen[row].getCell(col);
    }

    public Cell getAttributesAtPositionScrollback(int index, int col){
        if(index < 0 || index >= scrollbackSize || col < 0 || col >= width){
            throw new ScrollbackOutOfBoundsException(index, col);
        }

        Line line = scrollback[index];

        if(line == null){
            throw new ScrollbackEmptySlotException(index);
        }

        return line.getCell(col);
    }

    private void scroll(){
        // push top line to scrollback
        addToScrollBack(screen[0]);

        // shift screen up
        for (int i = 1; i < height; i++) {
            screen[i - 1] = screen[i];
        }

        // new empty line at bottom
        screen[height - 1] = new Line(width);
    }

    private void newLine() {
        cursor.setCol(0);
        cursor.moveDown();

        if(cursor.getRow() >= height) {
            scroll();
            cursor.setRow(height - 1);
        }
    }

    public void write(String text){
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '\n') {
                newLine();
                continue;
            }

            if (cursor.getCol() >= width) {
                newLine();
            }

            screen[cursor.getRow()].setChar(cursor.getCol(), c);
            screen[cursor.getRow()].setForeground(currentForeground, cursor.getCol(), cursor.getCol() + 1);
            screen[cursor.getRow()].setBackground(currentBackground, cursor.getCol(), cursor.getCol() + 1);
            screen[cursor.getRow()].setStyle(currentStyles, cursor.getCol(), cursor.getCol() + 1);

            cursor.moveRight();
        }
    }

    public void insertEmptyLine(){
        scroll();
    }

    public void clearScreen(){
        for (int i = 0; i < height; i++) {
            screen[i] = new Line(width);
        }
        cursor.setRow(0);
        cursor.setCol(0);
    }

    public void clearScreenAndScrollBack(){
        clearScreen();
        for(int i = 0; i < scrollbackMaxSize; i++){
            scrollback[i] = null;
        }
        scrollbackSize = 0;
        scrollbackStart = 0;
    }

    public char getCharAtPositionScreen(int row, int col){
        if(row < 0 || row >= height || col < 0 || col >= width){
            throw new ScreenOutOfBoundsException(row, col);
        }
        return screen[row].getChar(col);
    }

    public char getCharAtPositionScrollback(int index, int col){
        if(index < 0 || index >= scrollbackMaxSize){
            throw new ScrollbackOutOfBoundsException(index, col);
        }

        Line line = scrollback[index];
        if(line == null){
            throw new ScrollbackEmptySlotException(index);
        }

        if(col < 0 || col >= line.length()){
            throw new ScrollbackOutOfBoundsException(index, col);
        }

        return line.getChar(col);
    }

    public String scrollbackToString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < scrollbackMaxSize; i++) {
            int index = (scrollbackStart + i) % scrollbackMaxSize;
            Line line = scrollback[index];

            sb.append(i).append(": ");

            if (line == null) {
                sb.append("Empty Slot");
            } else {
                sb.append(line.toString());
            }

            sb.append('\n');
        }

        return sb.toString();
    }

    public String screeenToString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < height; i++) {
            String line = screen[i].toString();
            sb.append(line);

            if ( i < height - 1) sb.append('\n');
        }

        return sb.toString();
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("-------------------------------------\n");
        sb.append("    SCROLLBACK SECTION\n");
        sb.append("-------------------------------------\n");
        sb.append(scrollbackToString());

        sb.append("-------------------------------------\n");
        sb.append("    SCREEN SECTION\n");
        sb.append("-------------------------------------\n");
        sb.append(screeenToString());

        return sb.toString();
    }
}
