package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class MASTER {
    public DRAW FirstDraw= new DRAW();
    FIRST_PLAYER_SEAT FirstPlayerSeat= new FIRST_PLAYER_SEAT();
    public ROUND round= new ROUND();
    List<PLAYER> player= new ArrayList<>(0);
    int i;


    public int CheckCommonGoal(BOOKSHELF bookshelf, boolean goal1reached, boolean goal2reached){
        return FirstDraw.check(bookshelf, goal1reached, goal2reached);
    }

    public void setFirstDraw(int playerNumber){
        FirstDraw.setFirstDraw(playerNumber);
    }

    public void ChooseFirstPlayerSeat(){
        round.set(player.size());
        FirstPlayerSeat.choose(this.player);
        i= FirstPlayerSeat.n;
    }

    public String ChooseNextPlayer(){
        if(i==player.size()-1){
            i=0;
        }else{i++;}
        return player.get(i).username;
    }

    public boolean checkIfLastTurn(BOOKSHELF bookshelf){
        round.update(bookshelf);
        return round.last && (player.get(i).username.equals(FirstPlayerSeat.username));
    }

}
