package Figures;

public class Queen extends Figure {
    public Queen(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (Math.abs(row - row1) == Math.abs(col - col1) || row == row1 || col == col1) {
            return true;
        }
        return false;
    }
}
