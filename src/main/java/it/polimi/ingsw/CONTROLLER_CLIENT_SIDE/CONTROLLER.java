package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.MODEL.*;

import java.util.List;

public class CONTROLLER {
    public String username;
    public boolean LobbyIsFull=false;
    public int LobbySize;
    public item[][] grid;
    public boolean myTurn;


    /**
     * ask player to choose a new username.
     * @return username
     */
    public String getUsername(){
        return new String();
    }


    /**
     * ask player to choose a playerNumber for the match.
     * @return playerNumber
     */
    public int getLobbySize(){

        return 0;
    }

    public void setCommonGoals(List<COMMON_GOAL_CARD> list){
        /**
         * set the view with the token_values and cards.
          */
    }

    public void setPersonalGoal(PERSONAL_GOAL_CARD card){
        /**
         * set the card on view.
         */
    }
}
