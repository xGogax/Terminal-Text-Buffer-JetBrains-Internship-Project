package test;

import terminal.Color;
import terminal.StyleFlag;
import terminal.TerminalBuffer;

public class TestTerminalBuffer {

    public static void main(String[] args) {

        System.out.println("========== TERMINAL BUFFER TEST ==========");

        TerminalBuffer buffer = new TerminalBuffer(10, 4, 5);

        // BASIC WRITE
        System.out.println("\n--- WRITE TEST ---");

        buffer.write("Hello");
        assert buffer.getCharAtPositionScreen(0,0) == 'H';
        assert buffer.getCharAtPositionScreen(0,4) == 'o';

        System.out.println(buffer);


        // NEWLINE + WRAP
        System.out.println("\n--- NEWLINE TEST ---");

        buffer.write("\nWorld");
        assert buffer.getCharAtPositionScreen(1,0) == 'W';

        System.out.println(buffer);


        // COLOR + STYLE ATTRIBUTES
        System.out.println("\n--- ATTRIBUTE TEST ---");

        buffer.setCurrentForeground(Color.RED);
        buffer.setCurrentStyles(new StyleFlag[]{StyleFlag.BOLD});

        buffer.write("\nA");

        assert buffer.getAttributesAtPositionScreen(2,0).getForegroundColor() == Color.RED;

        buffer.setDefaultAttributes();

        System.out.println(buffer);


        // INSERT TEST
        System.out.println("\n--- INSERT TEST ---");

        buffer.clearScreen();

        buffer.write("ABCDE");

        buffer.getCursor().setRow(0);
        buffer.getCursor().setCol(2);

        buffer.insert("X");

        assert buffer.getCharAtPositionScreen(0,2) == 'X';

        System.out.println(buffer);


        // FILL LINE
        System.out.println("\n--- FILL LINE TEST ---");

        buffer.fillLine(1,'*');

        assert buffer.getCharAtPositionScreen(1,0) == '*';

        System.out.println(buffer);


        // WIDE CHARACTER
        System.out.println("\n--- WIDE CHAR TEST ---");

        buffer.clearScreen();

        buffer.write("你");

        assert buffer.getCharAtPositionScreen(0,0) == '你';

        System.out.println(buffer);


        // SCROLL TEST
        System.out.println("\n--- SCROLL TEST ---");

        buffer.write("\nLine1\nLine2\nLine3\nLine4\nLine5");

        System.out.println(buffer);

        System.out.println("Scrollback:");
        System.out.println(buffer.scrollbackToString());


        // GET LINE TEST
        System.out.println("\n--- GET LINE TEST ---");

        String line = buffer.getLineAtPositionScreen(0);

        assert line.length() == 10;

        System.out.println(line);


        // SCROLLBACK ACCESS
        System.out.println("\n--- SCROLLBACK ACCESS ---");

        if(buffer.scrollbackToString() != null){
            System.out.println("Scrollback exists");
        }


        // CURSOR MOVEMENT
        System.out.println("\n--- CURSOR TEST ---");

        buffer.getCursor().setRow(2);
        buffer.getCursor().setCol(3);

        buffer.write("Z");

        assert buffer.getCharAtPositionScreen(2,3) == 'Z';

        System.out.println(buffer);


        // RESIZE BIGGER
        System.out.println("\n--- RESIZE BIGGER ---");

        buffer.resize(15,6);

        System.out.println(buffer);


        // RESIZE SMALLER
        System.out.println("\n--- RESIZE SMALLER ---");

        buffer.resize(5,3);

        System.out.println(buffer);


        // CLEAR SCREEN
        System.out.println("\n--- CLEAR SCREEN ---");

        buffer.clearScreen();

        assert buffer.getCharAtPositionScreen(0,0) == ' ';

        System.out.println(buffer);


        // CLEAR SCROLLBACK
        System.out.println("\n--- CLEAR SCROLLBACK ---");

        buffer.clearScreenAndScrollBack();

        System.out.println(buffer);


        // OUT OF BOUNDS TEST
        System.out.println("\n--- OUT OF BOUNDS TEST ---");

        boolean caught = false;

        try{
            buffer.getCharAtPositionScreen(100,100);
        }catch(Exception e){
            caught = true;
        }

        assert caught;

        System.out.println("Exception correctly thrown");


        // EDGE CASE: EXACT WIDTH
        System.out.println("\n--- EDGE: EXACT WIDTH WRITE ---");

        buffer.clearScreen();

        buffer.write("1234567890");

        assert buffer.getCharAtPositionScreen(0,9) == '0';

        System.out.println(buffer);


        // EDGE CASE: WIDTH + 1
        System.out.println("\n--- EDGE: WIDTH + 1 ---");

        buffer.clearScreen();

        buffer.write("12345678901");

        assert buffer.getCharAtPositionScreen(1,0) == '1';

        System.out.println(buffer);


        // EDGE CASE: INSERT LAST COLUMN
        System.out.println("\n--- EDGE: INSERT LAST COLUMN ---");

        buffer.clearScreen();

        buffer.getCursor().setRow(0);
        buffer.getCursor().setCol(9);

        buffer.insert("Z");

        assert buffer.getCharAtPositionScreen(0,9) == 'Z';

        System.out.println(buffer);


        // EDGE CASE: WIDE CHAR AT EDGE
        System.out.println("\n--- EDGE: WIDE CHAR AT EDGE ---");

        buffer.clearScreen();

        buffer.getCursor().setCol(9);

        buffer.write("你");

        System.out.println(buffer);


        // EDGE CASE: CURSOR EXTREME VALUES
        System.out.println("\n--- EDGE: EXTREME CURSOR ---");

        buffer.getCursor().setRow(999);
        buffer.getCursor().setCol(999);

        buffer.write("X");

        System.out.println(buffer);


        // EDGE CASE: FILL LINE OUT OF RANGE
        System.out.println("\n--- EDGE: fillLine OUT OF BOUNDS ---");

        boolean caughtFill = false;

        try{
            buffer.fillLine(100,'#');
        }catch(Exception e){
            caughtFill = true;
        }

        assert caughtFill;


        // EDGE CASE: SMALL SCROLLBACK
        System.out.println("\n--- EDGE: SCROLLBACK OVERFLOW ---");

        TerminalBuffer smallBuffer = new TerminalBuffer(5,2,2);

        smallBuffer.write("A\nB\nC\nD\nE\n");

        System.out.println(smallBuffer);

        System.out.println("Scrollback:");
        System.out.println(smallBuffer.scrollbackToString());


        // EDGE CASE: EXTREME RESIZE
        System.out.println("\n--- EDGE: RESIZE WIDTH = 1 ---");

        buffer.clearScreen();

        buffer.write("ABCDE");

        buffer.resize(1,10);

        System.out.println(buffer);


        System.out.println("\n========== ALL TESTS PASSED ==========");
    }
}