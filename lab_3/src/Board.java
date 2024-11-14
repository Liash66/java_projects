import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

public class Board {
    private char colorGame;

    public void setColorGame(char colorGame) {
        this.colorGame = colorGame;
    }

    public char getColorGame() {
        return colorGame;
    }

    private Figure fields[][] = new Figure[8][8];

    public void init() {
        this.fields[1] = new Figure[]{
            new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
            new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),new Pawn("P", 'w'),
        };
        this.fields[6] = new Figure[] {
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),
                new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b'),new Pawn("P", 'b')
        };
        this.fields[0] = new Figure[] {
                new Rook("R", 'w'), new Knight("H", 'w'), new Bishop("B", 'w'), new Queen("Q", 'w'), new King("K", 'w'), new Bishop("B", 'w'), new Knight("H", 'w'), new Rook("R", 'w')
        };
        this.fields[7] = new Figure[] {
                new Rook("R", 'b'), new Knight("H", 'b'), new Bishop("B", 'b'), new Queen("Q", 'b'), new King("K", 'b'), new Bishop("B", 'b'), new Knight("H", 'b'), new Rook("R", 'b')
        };
    }

    public String getCell(int row, int col) {
        Figure figure = fields[row][col];
        if (figure == null) {
            return "    ";
        }
        return " " + figure.getColor() + figure.getName() + " ";
    }

    public void print_board() {
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row >= 0; row--) {
            System.out.print(row);
            for (int col = 0; col < 8; col++) {
                System.out.print("|" + getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }
        System.out.println("    0    1    2    3    4    5    6    7 ");
    }

    private boolean check_move(int row, int col, int row1, int col1) {
        if (fields[row][col] instanceof Knight) {
            return true;
        }

        int rowDir = 0;
        int colDir = 0;
    
        if (row1 > row) {
            rowDir = 1;
        } else if (row1 < row) {
            rowDir = -1;
        }
    
        if (col1 > col) {
            colDir = 1;
        } else if (col1 < col) {
            colDir = -1;
        }
    
        int tempRow = row + rowDir;
        int tempCol = col + colDir;
    
        while (tempRow != row1 || tempCol != col1) {
            if (fields[tempRow][tempCol] != null) {
                return false;
            }
            tempRow += rowDir;
            tempCol += colDir;
        }
        return true;
    }

    public boolean move_figure(int row, int col, int row1, int col1) {
        Figure figure = fields[row][col];

        if (figure.canMove(row, col, row1, col1) && check_move(row, col, row1, col1)) {
            Figure target = fields[row1][col1];
            if (target != null && target.getColor() == colorGame) {
                return false;
            }

            fields[row1][col1] = figure;
            fields[row][col] = null;

            if (isCheckmate(colorGame)) {
                fields[row][col] = figure;
                fields[row1][col1] = target;
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean isKingInCheck(char kingColor) {
        int kingRow = -1;
        int kingCol = -1;
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (fields[row][col] instanceof King && fields[row][col].getColor() == kingColor) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
            if (kingRow != -1) break;
        }
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (fields[row][col] != null && fields[row][col].getColor() != kingColor) {
                    if (fields[row][col].canMove(row, col, kingRow, kingCol) && check_move(row, col, kingRow, kingCol)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public boolean isCheckmate(char kingColor) {
        if (!isKingInCheck(kingColor)) return false;
    
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Figure figure = fields[row][col];
                if (figure != null && figure.getColor() == kingColor) {
                    for (int tempRow = 0; tempRow < 8; tempRow++) {
                        for (int tempCol = 0; tempCol < 8; tempCol++) {
                            Figure target = fields[tempRow][tempCol];
                            if (target != null && target.getColor() == kingColor) continue;
    
                            if (figure.canMove(row, col, tempRow, tempCol) && check_move(row, col, tempRow, tempCol)) {
                                fields[tempRow][tempCol] = figure;
                                fields[row][col] = null;
    
                                boolean stillInCheck = isKingInCheck(kingColor);
    
                                fields[row][col] = figure;
                                fields[tempRow][tempCol] = target;
    
                                if (!stillInCheck) return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}