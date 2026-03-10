package test;

import terminal.Cursor;

public class TestCursor {
    public static void main(String[] args) {
        // Create a cursor at position (5, 10)
        Cursor cursor = new Cursor(5, 10);

        // Print initial position
        System.out.println(cursor);

        // Move the cursor around
        cursor.moveUp(2);
        System.out.println("After moving up 2: " + cursor);
        cursor.moveDown(3);
        System.out.println("After moving down 3: " + cursor);
        cursor.moveLeft(4);
        System.out.println("After moving left 4: " + cursor);
        cursor.moveRight(5);
        System.out.println("After moving right 5: " + cursor);

        // Using moveUp(with no argument) and moveDown(with no argument)
        cursor.moveUp();
        System.out.println("After moving up 1: " + cursor);
        cursor.moveDown();
        System.out.println("After moving down 1: " + cursor);
    }
}
