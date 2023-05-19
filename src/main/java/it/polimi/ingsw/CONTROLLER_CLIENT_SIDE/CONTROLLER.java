package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;

import java.util.List;

public class CONTROLLER{
    public String username;
    public boolean firstToConnect = false;
    public boolean LoginAccepted = false;
    public boolean LobbyIsFull = false;
    public int LobbySize;

    public item[][] grid;
    public boolean myTurn = false;
    public String playerToPlay;
    public boolean update = false;
    public CLI cli = new CLI();

    public void notifyCLI(String message){
        cli.notify(message);
    }
    public String getUsername(){
        return cli.getUsername();
    }

    public int getLobbySize(){
        return cli.getLobbySize();
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
