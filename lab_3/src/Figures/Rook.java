package Figures;

public class Rook extends Figure {
    public Rook(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if (row == row1 || col == col1) {
            return true;
        }
        return false;
    }
}
