package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.MODEL.*;

import java.util.List;

public class CONTROLLER {
    public String username;
    public boolean firstToConnect = false;
    public boolean LoginAccepted = false;
    public boolean LobbyIsFull = false;
    public int LobbySize;

    public item[][] grid;
    public boolean myTurn = false;
    public String playerToPlay;


    public String getUsername(){
        //TODO
        return new String();
    }


    public int getLobbySize(){
        //TODO
        return 0;
    }

    public void setPlayerToPlay( String ptp){
        //TODO
    }
    public void setBoard( item[][] grid ){
        this.grid = grid;
        //TODO
    }

    public void setCommonGoals(List<COMMON_GOAL_CARD> list){
        //TODO
    }

    public void setPersonalGoal(PERSONAL_GOAL_CARD card){
        //TODO
    }



}
