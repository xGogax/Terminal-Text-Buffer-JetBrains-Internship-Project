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

    public int charWidth(char c) {
        if (Character.UnicodeScript.of(c) == Character.UnicodeScript.HAN ||
            Character.UnicodeScript.of(c) == Character.UnicodeScript.HIRAGANA ||
            Character.UnicodeScript.of(c) == Character.UnicodeScript.KATAKANA) {
            return 2;
        }
        return 1;
    }

    public char getChar(int index) {
        if (index < 0 || index >= cells.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + cells.length);
        }

        return cells[index].getCharacter();
    }

    public Cell getCell(int index) {
        if (index < 0 || index >= cells.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + cells.length);
        }

        return cells[index];
    }

    public void setChar(int index, char c) {
        if (index < 0 || index >= cells.length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + cells.length);
        }

        cells[index].setCharacter(c);
    }

    public void setWideChar(int index, char c) {
        if (index < 0 || index + 1 >= cells.length) {
            throw new IndexOutOfBoundsException("Wide char out of bounds at " + index);
        }

        cells[index].setCharacter(c);
        cells[index].setType(CellType.WIDE_START);

        cells[index + 1].setCharacter('\0');
        cells[index + 1].setType(CellType.WIDE_CONTINUE);
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
