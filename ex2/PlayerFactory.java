public class PlayerFactory {
    /**
     * build the players in the game
     * @param type type of the player
     * @return player object
     */
    public Player buildPlayer(String type){
        switch (type) {
            case "human":
                return new HumanPlayer();
            case "clever":
                return new CleverPlayer();
            case "whatever":
                return new WhateverPlayer();
            case "genius":
                return new GeniusPlayer();
            default:
                return null;
        }
    }
}
