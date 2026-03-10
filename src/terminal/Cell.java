package terminal;

public class Cell {
    private char character;

    private Color foregroundColor = Color.WHITE;
    private Color backgroundColor = Color.BLACK;
    private StyleFlag[] styleFlags = new StyleFlag[0];

    public Cell(char character) { this.character = character; }

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public char getCharacter() { return character; }
    public void setCharacter(char character) { this.character = character; }

    public Color getForegroundColor() { return foregroundColor; }
    public void setForegroundColor(Color foregroundColor) { this.foregroundColor = foregroundColor; }

    public Color getBackgroundColor() { return backgroundColor; }
    public void setBackgroundColor(Color backgroundColor) { this.backgroundColor = backgroundColor; }

    public StyleFlag[] getStyleFlags() { return styleFlags; }
    public void setStyleFlags(StyleFlag... flags) { this.styleFlags = flags; }
    // </editor-fold>
}
