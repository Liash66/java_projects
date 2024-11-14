package Figures;

public class King extends Figure {
    public King(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1) {
        if ((row+1==row1) || (row-1==row1) || ((row+1==row1) && (col+1==col1)) || ((row+1==row1) && (col-1==col1)) || ((row-1==row1) && (col+1==col1)) || ((row-1==row1) && (col-1==col1))) {
            return true;
        }
        return false;
    }
}
