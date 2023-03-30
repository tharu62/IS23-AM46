package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class SPACE extends BOARD {
    BOARD board= new BOARD();
    List<PLAYER> player= new ArrayList<PLAYER>(1);
    int i=0;

    public void setBoard(int playerNumber){
        board.setGrid(playerNumber);
    }

    public boolean draw(String playerName, int n, int m){
        if(i>2){
            return false;
        }
        item temp=board.drawItem(n,m);
        if(temp!=item.EMPTY){
            player.get(1).bookshelf.itemToPut[i] = temp;
            i++;
            return true;
        }
        else{
            return false;
        }
    }

    public void placeItem(int m,int a, int b, int c){
        player.get(1).bookshelf.putItems(m,a,b,c);
    }

    public String calculateScore(){
        /** CALCOLO PER OGNI MODEL.PLAYER LO SCORE COME SOMMA TRA (ITEM ADIACENTI SU MODEL.BOOKSHELF E IL CHECK SU MODEL.PERSONAL_GOAL_CARD),
         * COMPARA GLI SCORE E RESTITUISCE IL VINCITORE.
         */
        return new String();
    }
}
