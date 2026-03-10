package terminal;

public class Cursor {
    private int row;
    private int col;

    public Cursor(int row, int col){
        this.row = row;
        this.col = col;
    }

    public void moveUp(int n){ this.row -= n; }
    public void moveUp(){ moveUp(1); }

    public void moveDown(int n){ this.row += n; }
    public void moveDown(){ moveDown(1); }

    public void moveLeft(int n){ this.col -= n; }
    public void moveLeft(){ moveLeft(1); }

    public void moveRight(int n){ this.col += n; }
    public void moveRight(){ moveRight(1); }

    public int getRow(){ return row; }
    public int getCol(){ return col; }
    public void setRow(int row){ this.row = row; }
    public void setCol(int col){ this.col = col; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cursor Position: (").append(row).append(", ").append(col).append(")");
        return sb.toString();
    }
}
