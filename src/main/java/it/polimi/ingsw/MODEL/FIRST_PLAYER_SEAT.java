package it.polimi.ingsw.MODEL;

import java.util.List;
import java.util.Random;

public class FIRST_PLAYER_SEAT {
    int n;
    String username;

    /**
     * This method randomly chooses the player for the First Player Seat from a list of players.
     *
     * @param players is the list of the players.
     */
    public void choose(List<PLAYER> players){
        Random rand = new Random();
        int upperbound = players.size();
        this.n = rand.nextInt(upperbound);
        this.username = players.get(n).username;
    }

}
