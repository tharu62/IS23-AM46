package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class MASTER {
    public DRAW FirstDraw = new DRAW();
    FIRST_PLAYER_SEAT FirstPlayerSeat = new FIRST_PLAYER_SEAT();
    public ROUND round = new ROUND();
    List<PLAYER> player = new ArrayList<>(0);
    int i;

    /**
     * This method check in the bookshelf gives as parameter if the goals of the Common Goal Cards have been reached.
     * If a goal has been reached the token from the Common Goal Card is updated.
     *
     * @param bookshelf
     * @param goal1reached
     * @param goal2reached
     * @return the score of the Token In the Common Goal Card.
     */
    public int CheckCommonGoal(BOOKSHELF bookshelf, boolean goal1reached, boolean goal2reached){
        return FirstDraw.check(bookshelf, goal1reached, goal2reached);
    }

    /**
     * This method starts the method to draw and set the Common Goals.
     *
     * @param playerNumber is the number of player is the game.
     */
    public void setFirstDraw(int playerNumber){
        FirstDraw.setFirstDraw(playerNumber);
    }

    /**
     * This method set the size of the rounds and chooses the First Player Seat, then it set the start of the index of the
     * ChooseNextPlayer() method to the index of the First Player Seat chosen before.
     */
    public void ChooseFirstPlayerSeat(){
        round.set(player.size());
        FirstPlayerSeat.choose(this.player);
        i = FirstPlayerSeat.n;
    }

    /**
     * This method return the next player in the player list. If it's at the end of the list it re-starts from index 0.
     *
     * @return the username of the chosen player.
     */
    public String ChooseNextPlayer(){
        if(i==player.size()-1){
            i=0;
        }else{i++;}
        return player.get(i).username;
    }

    /**
     * This method checks if the bookshelf given as parameter is full.
     * If the bookshelf is full the round is set to last.
     * If the bookshelf is full and the player is the First player seat, the ROUND and the TURN is set to last.
     *
     * @param bookshelf is the bookshelf of a player.
     * @return true only if it's the last turn.
     */
    public boolean checkIfLastTurn(BOOKSHELF bookshelf){
        round.update(bookshelf);
        return round.last && (player.get(i).username.equals(FirstPlayerSeat.username));
    }

}
