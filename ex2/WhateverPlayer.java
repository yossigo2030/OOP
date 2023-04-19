import java.util.Random;

/**
 * player that play random
 */
public class WhateverPlayer implements Player{
    @Override
    public void playTurn(Board board, Mark mark) {
        while (true){
            Random num = new Random();
            int row = num.nextInt(board.getSize());
            int col = num.nextInt(board.getSize());
            if(board.putMark(mark, row, col)){
                break;
            }
        }
    }
}
