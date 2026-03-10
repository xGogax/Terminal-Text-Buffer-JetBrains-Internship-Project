package test;

import terminal.Color;
import terminal.Line;
import terminal.StyleFlag;

public class TestLine {
        public static void main(String[] args) {

            // Test 1: Basic text writing and default styles/colors
            Line line1 = new Line(20);
            line1.writeText("echo \"Hello World\"");
            line1.setForeground(Color.YELLOW, 0, 4);
            line1.setForeground(Color.GREEN, 5, line1.length());
            line1.setStyle(new StyleFlag[]{StyleFlag.BOLD}, 0, 4);
            line1.setStyle(new StyleFlag[]{StyleFlag.UNDERLINE, StyleFlag.ITALIC}, 5, 18);

            // Test 2: Text writing with offset and partial styling
            Line line2 = new Line(30);
            line2.writeText("This is a test line", 5);
            line2.setForeground(Color.CYAN, 5, 9);
            line2.setForeground(Color.MAGENTA, 10, line2.length());
            line2.setStyle(new StyleFlag[]{StyleFlag.STRIKETHROUGH}, 5, 9);
            line2.setStyle(new StyleFlag[]{StyleFlag.BOLD, StyleFlag.UNDERLINE}, 10, line2.length());

            // Test 3: throws exception when getting out of bounds
            Line line3 = new Line(10);
            try {
                line3.writeText("This text is too long for the line");
                line3.getChar(50);
                line3.getChar(-20);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Expected exception: " + e.getMessage());
            }

            System.out.println(line1);
            System.out.println(line2);
            System.out.println(line3);
        }
}
