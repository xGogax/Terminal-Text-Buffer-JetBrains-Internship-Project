package test;

import terminal.Cell;
import terminal.Color;
import terminal.StyleFlag;
import terminal.CellType;

public class TestCell {
    public static void main(String[] args) {
        // ----- NORMAL CELL TEST -----
        Cell cell1 = new Cell('A');
        Cell cell2 = new Cell('B');
        Cell cell3 = new Cell(' ');
        Cell cell4 = new Cell('C');

        cell1.setStyleFlags(StyleFlag.BOLD, StyleFlag.UNDERLINE);
        cell2.setForegroundColor(Color.BRIGHT_GREEN);
        cell2.setBackgroundColor(Color.GREEN);
        cell3.setForegroundColor(Color.WHITE);
        cell3.setBackgroundColor(Color.BRIGHT_MAGENTA);
        cell3.setStyleFlags(StyleFlag.ITALIC);
        cell4.setForegroundColor(Color.CYAN);
        cell4.setStyleFlags(StyleFlag.STRIKETHROUGH, StyleFlag.BOLD, StyleFlag.ITALIC, StyleFlag.UNDERLINE);

        // ---- ASSERTS FOR NORMAL CELLS ----
        assert cell1.getCharacter() == 'A' : "cell1 character failed";
        assert cell1.getForegroundColor() == Color.WHITE : "cell1 FG failed";
        assert cell1.getBackgroundColor() == Color.BLACK : "cell1 BG failed";
        assert cell1.getStyleFlags().length == 2 : "cell1 style length failed";

        assert cell2.getCharacter() == 'B' : "cell2 character failed";
        assert cell2.getForegroundColor() == Color.BRIGHT_GREEN : "cell2 FG failed";
        assert cell2.getBackgroundColor() == Color.GREEN : "cell2 BG failed";
        assert cell2.getStyleFlags().length == 0 : "cell2 style length failed";

        assert cell3.getCharacter() == ' ' : "cell3 character failed";
        assert cell3.getForegroundColor() == Color.WHITE : "cell3 FG failed";
        assert cell3.getBackgroundColor() == Color.BRIGHT_MAGENTA : "cell3 BG failed";
        assert cell3.getStyleFlags().length == 1 : "cell3 style length failed";

        assert cell4.getCharacter() == 'C' : "cell4 character failed";
        assert cell4.getForegroundColor() == Color.CYAN : "cell4 FG failed";
        assert cell4.getStyleFlags().length == 4 : "cell4 style length failed";

        // ----- WIDE CHARACTER TEST -----
        Cell wideCell = new Cell('你');
        wideCell.setType(CellType.WIDE_START);
        assert wideCell.getCharacter() == '你' : "wideCell character failed";
        assert wideCell.getType() == CellType.WIDE_START : "wideCell type WIDE_START failed";

        wideCell.setType(CellType.WIDE_CONTINUE);
        assert wideCell.getCharacter() == '你' : "wideCell character should stay same";
        assert wideCell.getType() == CellType.WIDE_CONTINUE : "wideCell type WIDE_CONTINUE failed";

        // ----- ALL COLOR COMBINATIONS TEST -----
        Cell colorTestCell = new Cell('X');
        for(Color bg : Color.values()){
            for(Color fg : Color.values()){
                colorTestCell.setBackgroundColor(bg);
                colorTestCell.setForegroundColor(fg);
                assert colorTestCell.getForegroundColor() == fg : "FG mismatch";
                assert colorTestCell.getBackgroundColor() == bg : "BG mismatch";
            }
        }

        System.out.println("All Cell tests passed!");
    }
}