package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class MASTER {
    DRAW FirstDraw= new DRAW();
    FIRST_PLAYER_SEAT FirstPlayerSeat= new FIRST_PLAYER_SEAT();
    ROUND round= new ROUND();
    List<PLAYER> player= new ArrayList<PLAYER>(0);
    int i;


    public int CheckCommonGoal(BOOKSHELF bookshelf){
        return FirstDraw.check(bookshelf);
    }

    public void setFirstDraw(int playerNumber){
        FirstDraw.setFirstDraw(playerNumber);
    }

    public void ChooseFirstPlayerSeat(){
        round.setLast(player.size());                           /** SUCCEDE SOLO UNA VOLTA PRIMA DI INIZIARE IL PRIMO ROUND **/
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
        if(round.last && (player.get(i).username.equals(FirstPlayerSeat.username))){return true;}
        return false;
    }

}
