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

    public String getLineAtPositionScreen(int row){
        if(row < 0 || row >= height){
            throw new ScreenOutOfBoundsException(row, 0);
        }

        Line line = screen[row];
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < width; i++){
            sb.append(line.getChar(i));
        }

        return sb.toString();
    }

    public String getLineAtPositionScrollback(int index){
        if(index < 0 || index >= scrollbackSize){
            throw new ScrollbackOutOfBoundsException(index, 0);
        }

        Line line = scrollback[index];
        if(line == null){
            throw new ScrollbackEmptySlotException(index);
        }

        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < width; i++){
            sb.append(line.getChar(i));
        }

        return sb.toString();
    }

    private void clampCursor() {
        if (cursor.getRow() < 0) cursor.setRow(0);
        if (cursor.getRow() >= height) cursor.setRow(height - 1);

        if (cursor.getCol() < 0) cursor.setCol(0);
        if (cursor.getCol() >= width) cursor.setCol(width - 1);

    }

    public void fillLine(int row, char c) {
        if (row < 0 || row >= height) {
            throw new ScreenOutOfBoundsException(row, 0);
        }

        Line line = screen[row];
        System.out.println(line);

        for (int col = 0; col < width; col++) {
            Cell cell = line.getCell(col);

            cell.setCharacter(c);
            cell.setForegroundColor(currentForeground);
            cell.setBackgroundColor(currentBackground);
            cell.setStyleFlags(currentStyles);
        }
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
        clampCursor();

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == '\n') {
                newLine();
                continue;
            }

            if (cursor.getCol() >= width) {
                newLine();
            }

            int charWidth = screen[cursor.getRow()].charWidth(c);
            Line line = screen[cursor.getRow()];

            if (charWidth == 1) {
                line.setChar(cursor.getCol(), c);

                line.setForeground(currentForeground, cursor.getCol(), cursor.getCol() + 1);
                line.setBackground(currentBackground, cursor.getCol(), cursor.getCol() + 1);
                line.setStyle(currentStyles, cursor.getCol(), cursor.getCol() + 1);

                cursor.moveRight();

            } else {

                if (cursor.getCol() + charWidth > width) {
                    newLine();
                    line = screen[cursor.getRow()];
                }

                line.setWideChar(cursor.getCol(), c);

                line.setForeground(currentForeground, cursor.getCol(), cursor.getCol() + charWidth);
                line.setBackground(currentBackground, cursor.getCol(), cursor.getCol() + charWidth);
                line.setStyle(currentStyles, cursor.getCol(), cursor.getCol() + charWidth);

                cursor.setCol(cursor.getCol() + charWidth);
            }
        }
    }

    public void insert(String text) {
        clampCursor();

        for (int i = 0; i < text.length(); i++) {

            if (cursor.getCol() >= width) {
                newLine();
            }

            Line line = screen[cursor.getRow()];

            // shift line right
            for (int col = width - 1; col > cursor.getCol(); col--) {
                Cell from = line.getCell(col - 1);
                Cell to = line.getCell(col);

                to.setCharacter(from.getCharacter());
                to.setForegroundColor(from.getForegroundColor());
                to.setBackgroundColor(from.getBackgroundColor());
                to.setStyleFlags(from.getStyleFlags());
                to.setType(from.getType());
            }

            // write char normally
            write(String.valueOf(text.charAt(i)));
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
        if(index < 0 || index >= scrollbackSize){
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

    public String screenToString() {
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

    public void setDefaultAttributes() {
        this.currentForeground = Color.WHITE;
        this.currentBackground = Color.BLACK;
        this.currentStyles = new StyleFlag[0];
    }

    public Color getCurrentForeground () {
        return currentForeground;
    }
    public void setCurrentForeground (Color color) {
        this.currentForeground = color;
    }

    public Color getCurrentBackground () {
        return currentBackground;
    }
    public void setCurrentBackground (Color color) {
        this.currentBackground = color;
    }

    public StyleFlag[] getCurrentStyles () {
        return currentStyles;
    }
    public void setCurrentStyles (StyleFlag[] styles) {
        this.currentStyles = styles;
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
        sb.append(screenToString());

        return sb.toString();
    }
}
