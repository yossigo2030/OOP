/**
 * play tournament of games
 */
public class Tournament {

    private final int rounds;
    private final Renderer renderer;
    private final Player[] players;

    int[] winsArr = new int[] {0, 0, 0};
    static String[] names = {"human", "clever", "whatever", "genius"};
    static String ERROR_NAME_MASSAGE ="Choose a player, and start again\n" +
            "The players: [human, clever, whatever, genius]\n";


    /**
     * constructor
     * @param rounds rounds in the tournament
     * @param renderer render
     * @param players which type of player
     */
    public Tournament(int rounds, Renderer renderer, Player[] players){
        this.rounds = rounds;
        this.renderer = renderer;
        this.players = new Player[] {players[0], players[1]};
    }

    /**
     * update the winner of spesific game
     * @param winner winner
     * @param num num
     */
    private void updateWInner(Mark winner, int num){
        switch (winner) {
            case X:
                winsArr[num % 2]++;
            case O:
                winsArr[(num + 1) % 2]++;
            case BLANK:
                winsArr[2]++;
        }
    }

    /**
     * run tournament and print the result after it
     * @param size size of the board
     * @param winStreak length of win
     * @param playerNames type of the players
     */
    public void playTournament(int size, int winStreak, String[] playerNames){
        for(int i = 0; i < rounds; i++) {
            Game game = new Game(players[i % 2], players[(i+1) % 2], size, winStreak,  renderer);
            Mark winner = game.run();
            updateWInner(winner, i);
        }
        System.out.printf("######### Results #########\n"
                + "Player 1, " +  playerNames[0] + " won: " + winsArr[0] + " rounds\n"
                + "Player 2, " +  playerNames[1] + " won: " + winsArr[1] + " rounds\n"
                + "Ties: " + winsArr[2]);
    }

    /**
     * main function that run all the tournament
     * @param args args
     */
    public static void main(String[] args) {
        String rounds = args[0];
        int boardSize = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        String renderType = args[3];
        String[] playerNames = new String[2];
        playerNames[0] = args[4].toLowerCase();
        playerNames[1] = args[5].toLowerCase();
        try {
            boolean name1 = false;
            boolean name2 = false;
            for (String name : names){
                if(name.equals(playerNames[0])){
                    name1 = true;
                }
                if(name.equals(playerNames[1])){
                    name2 = true;
                }
            }
            if(!name1 || !name2){
                throw new Exception(ERROR_NAME_MASSAGE);
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            return;
        }
        PlayerFactory playerFactory = new PlayerFactory();
        Player[] players = new Player[2];
        players[0] = playerFactory.buildPlayer(playerNames[0]);
        players[1] = playerFactory.buildPlayer(playerNames[1]);
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(renderType, boardSize);
        Tournament tournament = new Tournament(Integer.parseInt(rounds), renderer, players);
        tournament.playTournament(boardSize, winStreak, playerNames);
    }
}
