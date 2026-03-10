package terminal;

public class Cell {
    private char character;

    private Color foregroundColor = Color.WHITE;
    private Color backgroundColor = Color.BLACK;
    private StyleFlag[] styleFlags = new StyleFlag[0];

    public Cell(char character) { this.character = character; }

    @Override
    public String toString() {
        int fg = foregroundColor.getAnsiForeground();
        int bg = backgroundColor.getAnsiBackground();

        // styleCodeFormat example: "1;4;3" for BOLD, UNDERLINE, ITALIC
        StringBuilder styleCode = new StringBuilder();
        for (int i = 0; i < styleFlags.length; i++) {
            styleCode.append(styleFlags[i].getCode());
            if (i < styleFlags.length - 1) styleCode.append(";");
        }

        String style = !styleCode.isEmpty() ? styleCode.toString() + ";" : "";

        return "\u001B[" + style + fg + ";" + bg + "m" + character + "\u001B[0m";
    }

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
