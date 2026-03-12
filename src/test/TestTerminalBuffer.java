package test;

import terminal.Color;
import terminal.TerminalBuffer;

import javax.swing.*;

public class TestTerminalBuffer {
    public static void main(String[] args) {
        TerminalBuffer buffer = new TerminalBuffer(20, 5, 7);

        buffer.write("echo \"Hello World\"\n");
        buffer.setCurrentForeground(Color.YELLOW);
        buffer.write("Hello World\n");
        buffer.setDefaultAttributes();
        buffer.write("time\n");
        buffer.write("14:02:15\n");
        buffer.write("date\n");
        buffer.write("2024-06-01\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line");
        buffer.getCursor().moveRight(6);
        buffer.write("你");
        System.out.println(buffer.getCursor());

        buffer.getCursor().setRow(0);
        buffer.getCursor().setCol(15);

        buffer.write("Overwrite");
        buffer.fillLine(3, 'A');

        System.out.println(buffer.getCharAtPositionScreen(2, 2));
        System.out.println(buffer.getLineAtPositionScreen(0));
        System.out.println(buffer.getLineAtPositionScreen(3));

        buffer.getCursor().setCol(2);
        buffer.getCursor().setRow(0);
        buffer.insert("Hi");
        System.out.println(buffer);
    }
}
