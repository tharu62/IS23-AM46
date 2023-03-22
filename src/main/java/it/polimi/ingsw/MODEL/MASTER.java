package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class MASTER {
    DRAW FirstDraw= new DRAW();
    FIRST_PLAYER_SEAT FirstPlayerSeat= new FIRST_PLAYER_SEAT();
    ROUND round= new ROUND();
    List<PLAYER> player= new ArrayList<PLAYER>(0);
    boolean goal_reached;


    public int CheckCommonGoal(BOOKSHELF bookshelf){
        return FirstDraw.check(bookshelf);
    }

    public void setFirstDraw(int playerNumber){
        FirstDraw.setFirstDraw(playerNumber);
    }

    public void ChooseFirstPlayerSeat(){

    }

    public String ChooseNextPlayer(){
        String username= new String();
        //
        return username;
    }

    public boolean checkIfLastTurn(BOOKSHELF bookshelf){
        return true;
    }


}
