/**
 * file that represent the board of the game
 */
public class Board {

    private Mark[][] board;
    private int size = 4;

    Board(){
        this.board = new Mark[size][size];
        makeBoard(this.size);
    }
    Board(int size){
        this.size = size;
        this.board = new Mark[size][size];
        makeBoard(this.size);
    }

    /**
     * initlize board
     * @param size size of board
     */
    private void makeBoard(int size){
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    /**
     * return board size
     * @return board size
     */
    public int getSize(){
        return size;
    }

    /**
     * return board object
     * @return board
     */
    public Mark[][] getBoard(){
        return board;
    }

    /**
     * try to put mark on the board
     * @param mark mark
     * @param row row
     * @param col col
     * @return true of ok
     */
    public boolean putMark(Mark mark, int row, int col){
        if ((row >= 0 && row < size) && (col >= 0 && col < size)){
            if (board[row][col] == Mark.BLANK){
                board[row][col] = mark;
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * return the mark on the board
     * @param row row
     * @param col col
     * @return mark
     */
    public Mark getMark(int row, int col){
        if ((row >= 0 && row < size) && (col >= 0 && col < size)){
            return board[row][col];
        }
        return Mark.BLANK;
    }
}
