package test;

import terminal.Cursor;

public class TestCursor {
    public static void main(String[] args) {
        Cursor cursor = new Cursor(5, 10);

        // Initial position
        assert cursor.getRow() == 5 : "Row should be 5";
        assert cursor.getCol() == 10 : "Col should be 10";

        // Move up/down
        cursor.moveUp(2);
        assert cursor.getRow() == 3 : "After moveUp(2), row should be 3";
        cursor.moveDown(4);
        assert cursor.getRow() == 7 : "After moveDown(4), row should be 7";

        cursor.moveUp();
        assert cursor.getRow() == 6 : "After moveUp(), row should be 6";
        cursor.moveDown();
        assert cursor.getRow() == 7 : "After moveDown(), row should be 7";

        // Move left/right
        cursor.moveLeft(4);
        assert cursor.getCol() == 6 : "After moveLeft(4), col should be 6";
        cursor.moveRight(5);
        assert cursor.getCol() == 11 : "After moveRight(5), col should be 11";

        cursor.moveLeft();
        assert cursor.getCol() == 10 : "After moveLeft(), col should be 10";
        cursor.moveRight();
        assert cursor.getCol() == 11 : "After moveRight(), col should be 11";

        // Setters
        cursor.setRow(2);
        cursor.setCol(3);
        assert cursor.getRow() == 2 : "setRow failed";
        assert cursor.getCol() == 3 : "setCol failed";

        // Edge case: moving negative
        cursor.moveUp(5);
        cursor.moveLeft(4);
        assert cursor.getRow() == -3 : "Edge case row";
        assert cursor.getCol() == -1 : "Edge case col";

        // Move back into positive
        cursor.moveDown(10);
        cursor.moveRight(5);
        assert cursor.getRow() == 7 : "Row after moving down 10";
        assert cursor.getCol() == 4 : "Col after moving right 5";

        System.out.println("All Cursor tests passed!");
    }
}