package test;

import terminal.TerminalBuffer;

public class TestTerminalBuffer {
    public static void main(String[] args) {
        TerminalBuffer buffer = new TerminalBuffer(20, 5, 7);
        buffer.write("echo \"Hello World\"\n");
        buffer.write("Hello World\n");
        buffer.write("time\n");
        buffer.write("14:02:15\n");
        buffer.write("date\n");
        buffer.write("2024-06-01\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line\n");
        buffer.write("Another line");

        buffer.getCursor().setRow(0);
        buffer.getCursor().setCol(2);

        buffer.write("Overwrite");

        buffer.clearScreenAndScrollBack();
        System.out.println(buffer);
    }
}
