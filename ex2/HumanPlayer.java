import java.util.Scanner;

public class HumanPlayer implements Player{

    HumanPlayer(){

    }

    /**
     * get coordinate and play turn on game
     * @param board board
     * @param mark mark
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        boolean first = true;
        while (true) {
            if(first){
                System.out.println("Player" + mark + ", type coordinates: ");
                first = false;
            }
            Scanner index = new Scanner(System.in);
            int location = index.nextInt();
            int row = (location / 10);
            int col = (location % 10);
            if(!board.putMark(mark, row, col)){
                System.out.println("invalid coordinates, type again: ");
                continue;
            }
            break;
        }
    }
}
