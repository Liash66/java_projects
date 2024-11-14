package Figures;

public class Rook extends Figure {
    public Rook(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        for (int i = 0; i < 7; i++) {
            if ((row+i==row1) || (row-i==row1) || (col+i==col1) || (col-i==col1)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        for (int i = 0; i < 7; i++) {
            if ((row+i==row1) || (row-i==row1) || (col+i==col1) || (col-i==col1)){
                return true;
            }
        }
        return false;
    }
}
