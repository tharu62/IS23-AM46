package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class SPACE {
    BOARD board= new BOARD();
    List<PLAYER> player= new ArrayList<PLAYER>(1);
    int i=0;
    String winner;

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

    /** STO MODIFICANDO **/
    public void placeItem(int m,int a, int b, int c){
        player.get(1).bookshelf.putItems(m,a,b,c);
    }


    /** STO MODIFICANDO **/
    public String calculateScore(){
        /** CALCOLO PER OGNI MODEL.PLAYER LO SCORE COME SOMMA TRA (ITEM ADIACENTI SU MODEL.BOOKSHELF E IL CHECK SU MODEL.PERSONAL_GOAL_CARD),
         * COMPARA GLI SCORE E RESTITUISCE IL VINCITORE.
         */
        int tempScore=0;
        int winner=0;
        for(int i=0;i< player.size();i++){
            player.get(i).score+= player.get(i).personal.cardLogic.checkCardLogic(player.get(i).bookshelf);
            player.get(i).score+= player.get(i).bookshelf.checkAdjacentItem(item.BOOKS);
            player.get(i).score+= player.get(i).bookshelf.checkAdjacentItem(item.CATS);
            player.get(i).score+= player.get(i).bookshelf.checkAdjacentItem(item.FRAMES);
            player.get(i).score+= player.get(i).bookshelf.checkAdjacentItem(item.GAMES);
            player.get(i).score+= player.get(i).bookshelf.checkAdjacentItem(item.PLANTS);
            player.get(i).score+= player.get(i).bookshelf.checkAdjacentItem(item.TROPHIES);
            if(tempScore<player.get(i).score){
                tempScore=player.get(i).score;
                winner=i;
            }
        }
        this.winner=player.get(winner).username;
        return this.winner;
    }
}
