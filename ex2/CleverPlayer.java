import java.util.Random;

/**
 * clever player try to fill the first line in the begining
 */
public class CleverPlayer implements Player {
    @Override

    public void playTurn(Board board, Mark mark) {
        for (int i = 0; i < board.getSize(); i++) {
            if (board.getMark(0, i) == Mark.BLANK) {
                board.putMark(mark, 0, i);
                return;
            }
        }
        while (true) {
            Random num = new Random();
            int row = num.nextInt(board.getSize());
            int col = num.nextInt(board.getSize());
            if (board.putMark(mark, row, col)) {
                break;
            }
        }
    }
}
