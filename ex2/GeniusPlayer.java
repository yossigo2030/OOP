/**
 * genius player that put mark in the most length of the rival
 */
public class GeniusPlayer implements Player{
    private int boardSize;
    private Board board;
    @Override
    public void playTurn(Board board, Mark mark) {
        this.board = board;
        boardSize = board.getSize();
        if(isStart(board, mark)){
            board.putMark(mark, board.getSize()/2, board.getSize()/2);
        }
        else {
            blockRivalFromWin(mark);
        }
    }

    /**
     * check if the board is empty
     * @param board board
     * @param mark mark
     * @return true if ok
     */
    private boolean isStart(Board board, Mark mark){
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getMark(i, j) == mark) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * search the most length of the rival
     * @param mark mark
     */
    private void blockRivalFromWin(Mark mark) {
        int[] bestMove = {0, 0, 0};
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getMark(row, col) != mark && board.getMark(row, col) != Mark.BLANK) {
                    int[] index = bestLine(mark, row, col);
                    if (index[0] >= bestMove[0]) {
                        bestMove = index;
                    }
                }
            }
        }
        if(bestMove[0] != -1) {
            board.putMark(mark, bestMove[1], bestMove[2]);
        }
        else {
            WhateverPlayer newPlayer = new WhateverPlayer();
            newPlayer.playTurn(board, mark);
        }
    }

    /**
     * fine the best line of the rival
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] bestLine(Mark mark, int row, int col) {
        int[][] results = new int[8][3];
        int[] bestMove = {-1, 0, 0};
        results[0] = countLeftMarks(mark, row, col);
        results[1] = countRightMarks(mark, row, col);
        results[2] = countDownMarks(mark, row, col);
        results[3] = countUpMarks(mark, row, col);
        results[4] = countLeftUpMarks(mark, row, col);
        results[5] = countLeftDownMarks(mark, row, col);
        results[6] = countRightUpMarks(mark, row, col);
        results[7] = countRightDownMarks(mark, row, col);
        for (int[] list : results) {
            if (list[0] >= bestMove[0]) {
                bestMove = list;
            }
        }
        return bestMove;
    }

    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countLeftMarks(Mark mark, int row, int col) {
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = num[0] + 1;
            col -= 1;
            num[2] = col;
            if (validCoordinate(row, col)) {
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
        }
        return num;
    }
    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countRightMarks(Mark mark, int row, int col){
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK){
            num[0] = num[0] + 1;
            col += 1;
            num[2] = col;
            if (validCoordinate(row, col)){
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
        }
        return num;
    }
    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countUpMarks(Mark mark, int row, int col){
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK){
            num[0] = num[0] + 1;
            row -= 1;
            num[1] = row;
            if (validCoordinate(row, col)){
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
        }
        return num;
    }
    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countDownMarks(Mark mark, int row, int col) {
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = num[0] + 1;
            row += 1;
            num[1] = row;
            if (validCoordinate(row, col)) {
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
        }
        return num;
    }
    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countLeftUpMarks(Mark mark, int row, int col) {
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = num[0] + 1;
            row -= 1;
            col -= 1;
            num[1] = row;
            num[2] = col;
            if (validCoordinate(row, col)) {
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
        }
        return num;
    }
    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countLeftDownMarks(Mark mark, int row, int col) {
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = num[0] + 1;
            col -= 1;
            row += 1;
            num[1] = row;
            num[2] = col;
            if (validCoordinate(row, col)) {
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
        }
        return num;
    }
    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countRightUpMarks(Mark mark, int row, int col){
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = num[0] + 1;
            col += 1;
            row -= 1;
            num[1] = row;
            num[2] = col;
            if (validCoordinate(row, col)) {
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
        }
        return num;
    }
    /**
     * check the rival to the left
     * @param mark mark
     * @param row row
     * @param col col
     * @return list[length, row, col]
     */
    private int[] countRightDownMarks(Mark mark, int row, int col){
        int[] num = {0, row, col};
        boolean valid = true;
        while (board.getBoard()[row][col] != mark && board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = num[0] + 1;
            col += 1;
            row += 1;
            num[1] = row;
            num[2] = col;
            if (validCoordinate(row, col)) {
                continue;
            }
            valid = false;
            break;
        }
        if (!valid || board.getBoard()[row][col] != Mark.BLANK) {
            num[0] = -1;
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
        return (row >= 0 && row < boardSize) && (col >= 0 && col < boardSize);

    }
}
