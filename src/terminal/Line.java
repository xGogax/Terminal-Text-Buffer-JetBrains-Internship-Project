package terminal;

public class Line {
    private Cell[] cells;

    public Line(int width) {
        cells = new Cell[width];

        for (int i = 0; i < width; i++) {
            cells[i] = new Cell(' ');
        }
    }

    public int length() { return cells.length; }

    public char getChar(int index) {
        if (index < 0 || index >= cells.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + cells.length);
        }

        return cells[index].getCharacter();
    }

    public void setChar(int index, char c) {
        if (index < 0 || index >= cells.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + cells.length);
        }

        cells[index].setCharacter(c);
    }

    public void writeText(String text, int start) {
        if (start < 0) {
            throw new IllegalArgumentException("Start index cannot be negative: " + start);
        }

        for (int i = 0; i < text.length() && start + i < cells.length; i++) {
            cells[start + i].setCharacter(text.charAt(i));
        }
    }
    public void writeText(String text) {
        writeText(text, 0);
    }

    // Styles
    public void setStyle(StyleFlag[] flags, int start, int end) {
        if (start < 0 || end > cells.length) {
            throw new IndexOutOfBoundsException("Start: " + start + ", End: " + end + ", Length: " + cells.length);
        }

        for (int i = start; i < end; i++) {
            cells[i].setStyleFlags(flags);
        }
    }
    public void setStyle(StyleFlag... flags) {
        setStyle(flags, 0, cells.length);
    }

    // Foreground
    public void setForeground(Color color, int start, int end) {
        if (start < 0 || end > cells.length) {
            throw new IndexOutOfBoundsException("Start: " + start + ", End: " + end + ", Length: " + cells.length);
        }

        for (int i = start; i < end; i++) {
            cells[i].setForegroundColor(color);
        }
    }
    public void setForeground(Color color) {
        setForeground(color, 0, cells.length);
    }

    // Background
    public void setBackground(Color color, int start, int end) {
        if (start < 0 || end > cells.length) {
            throw new IndexOutOfBoundsException("Start: " + start + ", End: " + end + ", Length: " + cells.length);
        }

        for (int i = start; i < end; i++) {
            cells[i].setBackgroundColor(color);
        }
    }
    public void setBackground(Color color) {
        setBackground(color, 0, cells.length);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Cell cell : cells) {
            sb.append(cell.toString());
        }
        return sb.toString();
    }
}
