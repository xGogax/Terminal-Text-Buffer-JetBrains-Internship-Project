package test;

import terminal.Cell;
import terminal.CellType;
import terminal.Color;
import terminal.Line;
import terminal.StyleFlag;

public class TestLine {
    public static void main(String[] args) {
        System.out.println("-------------------------------------");
        System.out.println("    LINE CLASS COMPREHENSIVE TEST");
        System.out.println("-------------------------------------");

        // Test 1: Default line
        Line line1 = new Line(10);
        for (int i = 0; i < line1.length(); i++) {
            assert line1.getChar(i) == ' ' : "Default char should be space";
            assert line1.getCell(i).getForegroundColor() == Color.WHITE;
            assert line1.getCell(i).getBackgroundColor() == Color.BLACK;
            assert line1.getCell(i).getStyleFlags().length == 0;
        }

        // Test 2: WriteText and colors
        Line line2 = new Line(20);
        line2.writeText("HelloWorld");
        line2.setForeground(Color.YELLOW, 0, 5);
        line2.setForeground(Color.GREEN, 5, 10);
        line2.setBackground(Color.BLUE, 0, 10);
        line2.setStyle(new StyleFlag[]{StyleFlag.BOLD}, 0, 5);
        line2.setStyle(new StyleFlag[]{StyleFlag.UNDERLINE}, 5, 10);

        assert line2.getChar(0) == 'H';
        assert line2.getChar(9) == 'd';
        assert line2.getCell(0).getForegroundColor() == Color.YELLOW;
        assert line2.getCell(0).getBackgroundColor() == Color.BLUE;
        assert line2.getCell(0).getStyleFlags()[0] == StyleFlag.BOLD;
        assert line2.getCell(6).getForegroundColor() == Color.GREEN;
        assert line2.getCell(6).getStyleFlags()[0] == StyleFlag.UNDERLINE;

        System.out.println("Line2 visual output:");
        System.out.println(line2);

        // Test 3: Offset writeText
        Line line3 = new Line(15);
        line3.writeText("OffsetTest", 5);
        assert line3.getChar(5) == 'O';
        assert line3.getChar(14) == 't';

        System.out.println("Line3 visual output:");
        System.out.println(line3);

        // Test 4: Wide Characters
        Line line4 = new Line(5);
        line4.setWideChar(0, '你'); // CJK character
        line4.setChar(2, 'A');

        assert line4.getChar(0) == '你';
        assert line4.getCell(0).getType() == CellType.WIDE_START;
        assert line4.getCell(1).getType() == CellType.WIDE_CONTINUE;
        assert line4.getChar(2) == 'A';

        System.out.println("Line4 visual output (wide char):");
        System.out.println(line4);

        // Test 5: Out-of-bounds checks
        boolean caught = false;
        try {
            line4.getChar(10);
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        assert caught : "Expected IndexOutOfBoundsException for getChar";

        caught = false;
        try {
            line4.setChar(-1, 'X');
        } catch (IndexOutOfBoundsException e) {
            caught = true;
        }
        assert caught : "Expected IndexOutOfBoundsException for setChar";

        // Test 6: Styles and colors combinations
        Line line5 = new Line(8);
        line5.writeText("ABCDEFGH");
        line5.setForeground(Color.RED, 0, 4);
        line5.setForeground(Color.CYAN, 4, 8);
        line5.setBackground(Color.YELLOW, 0, 8);
        line5.setStyle(new StyleFlag[]{StyleFlag.BOLD, StyleFlag.UNDERLINE}, 0, 8);

        for (int i = 0; i < 4; i++) {
            assert line5.getCell(i).getForegroundColor() == Color.RED;
        }
        for (int i = 4; i < 8; i++) {
            assert line5.getCell(i).getForegroundColor() == Color.CYAN;
        }
        for (int i = 0; i < 8; i++) {
            assert line5.getCell(i).getBackgroundColor() == Color.YELLOW;
            assert line5.getCell(i).getStyleFlags().length == 2;
        }

        System.out.println("Line5 visual output (styles/colors):");
        System.out.println(line5);

        System.out.println("-------------------------------------");
        System.out.println("        ALL LINE TESTS PASSED!");
        System.out.println("-------------------------------------");
    }
}