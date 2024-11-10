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

    public  char getColorGame(){
        return colorGame;
    }


    private Figure fields[][] = new Figure[8][8];


    public void init(){
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

    public String getCell(int row, int col){
        Figure figure = this.fields[row][col];
        if (figure ==null){
            return "    ";
        }
        return  " "+figure.getColor()+figure.getName()+" ";
    }
    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for (int row = 7; row > -1 ; row --){
            System.out.print(row);
            for (int col=0; col<8; col++){
                System.out.print("|"+getCell(row, col));
            }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col=0; col< 8; col++){
            System.out.print("    "+col);
        }
    }

    public boolean check_move(Figure figure, int row, int col, int row1, int col1){
        switch (figure.getName()){
            case "P":
                if ((row+2==row1 || row-2==row1) &&
                (figure.getColor() == 'b' && this.fields[row - 1][col] != null ||
                        figure.getColor() == 'w' && this.fields[row + 1][col] != null)) {
                    return false;
                }
            case "R":
                if (row == row1){
                    for (int i = Math.min(col, col1); i < Math.max(col, col1); i++){
                        if (this.fields[row][i] != null){
                            return false;
                        }
                    }
                } else if (col == col1){
                    for (int i = Math.min(row, row1); i < Math.max(row, row1); i++){
                        if (this.fields[i][col] != null){
                            return false;
                        }
                    }
                }
            case "B":
                for (int i = 1; i < Math.abs(row - row1); i++){
                    if (row > row1 && col > col1 && this.fields[row-i][col-i] != null){
                        return false;
                    } else if (row > row1 && col < col1 && this.fields[row-i][col+i] != null){
                        return false;
                    } else if (row < row1 && col > col1 && this.fields[row+i][col-i] != null){
                        return false;
                    } else if (row < row1 && col < col1 && this.fields[row+i][col+i] != null){
                        return false;
                    }
                }
            case "Q":
                if (row == row1){
                    for (int i = Math.min(col, col1); i < Math.max(col, col1); i++){
                        if (this.fields[row][i] != null){
                            return false;
                        }
                    }
                } else if (col == col1){
                    for (int i = Math.min(row, row1); i < Math.max(row, row1); i++){
                        if (this.fields[i][col] != null){
                            return false;
                        }
                    }
                } else {
                    for (int i = 1; i < Math.abs(row - row1); i++){
                        if (row > row1 && col > col1 && this.fields[row-i][col-i] != null){
                            return false;
                        } else if (row > row1 && col < col1 && this.fields[row-i][col+i] != null){
                            return false;
                        } else if (row < row1 && col > col1 && this.fields[row+i][col-i] != null){
                            return false;
                        } else if (row < row1 && col < col1 && this.fields[row+i][col+i] != null){
                            return false;
                        }
                    }
                }
        }
        return true;
    }

    public boolean move_figure(int row, int col, int row1, int col1){
      Figure figure = this.fields[row][col];
      if (figure != null && figure.canMove(row, col, row1, col1) && this.fields[row1][col1] == null && figure.getColor() == this.colorGame && check_move(figure, row, col, row1, col1)){
          this.fields[row1][col1] = figure;
          this.fields[row][col] = null;
          return true;
      }else  if (figure.canAttack(row, col, row1, col1) && this.fields[row1][col1] != null && this.fields[row1][col1].getColor() != this.fields[row][col].getColor() && check_move(figure, row, col, row1, col1)){
          this.fields[row1][col1] = figure;
          this.fields[row][col] = null;
          return true;
      }
        return false;
    }
}
