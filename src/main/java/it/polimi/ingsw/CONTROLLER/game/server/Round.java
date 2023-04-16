package it.polimi.ingsw.CONTROLLER.game.server;

public class Round {
    private final ArrayList<Player> order = new ArrayList<>();
    private boolean firstTurn;
    private int index;
    private int roundNumber;

    /**
     * Round's constructor
     * @param order order of the player on their first round
     */
    public Round(List<Player> order)
    {
        firstTurn = true;
        this.order.addAll(order);
        index = 0;
        roundNumber = 1;}

    /**
     * Checks if it's the first turn
     * @return true during the first turn, false otherwise
     */
    public boolean getFirstTurn()
    {
        return firstTurn;
    }



    /**
     * Getter for the actual round number
     * @return actual round number
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * @return the nickname of the active player.
     */
    public String getActivePlayer() {
        return Player;
    }
}


