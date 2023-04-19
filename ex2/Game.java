public class Game {
    /**
     * run one game
     */

    private Player playerX;
    private Player playerO;
    private Renderer renderer;
    private int boardSize = 4;
    private int winStreak = 3;
    private int itemInBoard = 0;
    private Board board;

    private Mark winner = Mark.BLANK;

    /**
     * default constructor
     * @param playerX x
     * @param playerO o
     * @param renderer render
     */
    public Game(Player playerX, Player playerO, Renderer renderer){
        this.playerX = playerX;
        this.playerO = playerO;
        this.renderer = renderer;
    }

    /**
     * constructor
     * @param playerX player x
     * @param playerO player o
     * @param size board size
     * @param winStreak length of win
     * @param renderer render
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer){
        this.playerX = playerX;
        this.playerO = playerO;
        this.boardSize = size;
        if (winStreak > boardSize){
            this.winStreak = boardSize;
        }
        else{
            this.winStreak = winStreak;
        }
        this.renderer = renderer;

    }
    public int getWinStreak(){
        return winStreak;
    }

    /**
     * run one game
     * @return the winner
     */
    public Mark run(){
        this.board = new Board(boardSize);
        Player[] playersArr = {playerX, playerO};
        Mark[] marksArr = {Mark.X, Mark.O};
        int num = 0;
        boolean gameOver = false;
        while (!gameOver){
            int curTurn = num % 2;
            Player curPlayer = playersArr[curTurn];
            Mark curMark = marksArr[curTurn];
            curPlayer.playTurn(board, curMark);
            renderer.renderBoard(board);
            itemInBoard += 1;
            gameOver = gameOver(curMark);
            num += 1;
        }
        return getWinner();
    }

    /**
     * check if game ended
     * @return true if ended
     */
    private boolean gameOver(Mark mark){
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                boolean win = checkIfWin(mark, row, col);
                if(win){
                    return true;
                }
            }
        }
        return itemInBoard >= boardSize * boardSize;
    }

    /**
     * return the winner of the game
     * @return winner
     */
    private Mark getWinner(){
        return winner;
    }
    private boolean checkIfWin(Mark mark, int row, int col){
        if((countLeftMarks(mark, row, col) >= winStreak) || (countRightMarks(mark, row, col) >= winStreak) ||
                (countDownMarks(mark, row, col) >= winStreak) || (countUpMarks(mark, row, col) >= winStreak) ||
                (countLeftUpMarks(mark, row, col) >= winStreak) || (countLeftDownMarks(mark, row, col) >= winStreak) ||
                (countRightUpMarks(mark, row, col) >= winStreak) || (countRightDownMarks(mark, row, col) >= winStreak)){
            winner = mark;
            return true;
        }
        return false;
    }

    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countLeftMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            col -= 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }
    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countRightMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            col += 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }
    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countUpMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            row -= 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }
    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countDownMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            row += 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }
    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countLeftUpMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            col -= 1;
            row -= 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }
    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countLeftDownMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            col -= 1;
            row += 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }
    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countRightUpMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            col += 1;
            row -= 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }
    /**
     * count length in the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return length
     */
    private int countRightDownMarks(Mark mark, int row, int col){
        int num = 0;
        while (board.getBoard()[row][col] == mark){
            num += 1;
            col += 1;
            row += 1;
            if (validCoordinate(row, col)){
                continue;
            }
            break;
        }
        return num;
    }

    /**
     * check if coordinate arr valid
     * @param row row
     * @param col col
     * @return true if ok
     */
    private boolean validCoordinate(int row, int col){
        if ((row >= 0 && row < boardSize) && (col >= 0 && col < boardSize)){
            return true;
        }
        return false;

    }
}
