package test;

import terminal.Cell;
import terminal.Color;
import terminal.StyleFlag;

public class TestCell {
    public static void main(String[] args) {
        System.out.println("-------------------------------------");
        System.out.println("           NORMAL CELL TEST");
        System.out.println("-------------------------------------");

        Cell cell1 = new Cell('A');
        Cell cell2 = new Cell('B');
        Cell cell3 = new Cell(' ');
        Cell cell4 = new Cell('C');

        // Only StyleFlags test
        cell1.setStyleFlags(StyleFlag.BOLD, StyleFlag.UNDERLINE);

        // Only FG and BG test
        cell2.setForegroundColor(terminal.Color.BRIGHT_GREEN);
        cell2.setBackgroundColor(terminal.Color.GREEN);

        // All combined test
        cell3.setForegroundColor(terminal.Color.WHITE);
        cell3.setBackgroundColor(terminal.Color.BRIGHT_MAGENTA);
        cell3.setStyleFlags(StyleFlag.ITALIC);

        // Only FG and StyleFlags test
        cell4.setForegroundColor(terminal.Color.CYAN);
        cell4.setStyleFlags(StyleFlag.STRIKETHROUGH, StyleFlag.BOLD, StyleFlag.ITALIC, StyleFlag.UNDERLINE);

        // output
        System.out.println(cell1);
        System.out.println(cell2);
        System.out.println(cell3);
        System.out.println(cell4);

        System.out.println("-------------------------------------");
        System.out.println("    EVERY COLOR COMBINATION TEST");
        System.out.println("-------------------------------------");

        int i = 1;
        Cell cell = new Cell('X');
        for(Color backgroundColor : Color.values()){
            for(Color foregroundColor : Color.values()){
                cell.setBackgroundColor(backgroundColor);
                cell.setForegroundColor(foregroundColor);
                if(i % 4 == 0){
                    System.out.println(cell);
                } else {
                    System.out.print(cell);
                }
                i++;
            }
        }

        System.out.println("-------------------------------------");
        System.out.println("        WIDE CHARACTER TEST");
        System.out.println("-------------------------------------");

        // Test wide character handling
        Cell wideCell = new Cell('你'); // CJK character
        wideCell.setType(terminal.CellType.WIDE_START);
        System.out.println("Wide char start: " + wideCell);
        wideCell.setType(terminal.CellType.WIDE_CONTINUE);
        System.out.println("Wide char continue (should be empty): " + wideCell);

        // Test getters
        assert wideCell.getCharacter() == '你';
        assert wideCell.getType() == terminal.CellType.WIDE_CONTINUE;
    }
}
