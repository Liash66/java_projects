package Figures;

public class Knight extends Figure {
    public Knight(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
//        if (!super.canMove(row, col, row1, col1)){
//            return false;
//        }
        if (((row+1==row1) && (col+2==col1)) || ((row+1==row1) && (col-2==col1)) || ((row-1==row1) && (col+2==col1)) || ((row-1==row1) && (col-2==col1)) || ((row+2==row1) && (col+1==col1)) || ((row+2==row1) && (col-1==col1)) || ((row-2==row1) && (col+1==col1)) || ((row-2==row1) && (col-1==col1))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1) {
        if (((row+1==row1) && (col+2==col1)) || ((row+1==row1) && (col-2==col1)) || ((row-1==row1) && (col+2==col1)) || ((row-1==row1) && (col-2==col1)) || ((row+2==row1) && (col+1==col1)) || ((row+2==row1) && (col-1==col1)) || ((row-2==row1) && (col+1==col1)) || ((row-2==row1) && (col-1==col1))) {
            return true;
        }
        return false;
    }
}
