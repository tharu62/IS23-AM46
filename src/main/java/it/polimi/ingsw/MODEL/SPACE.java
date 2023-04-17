package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class SPACE {
    public BOARD board= new BOARD();
    public List<PLAYER> player= new ArrayList<>(1);
    int i=0;
    String winner;

    public void setBoard(int playerNumber){
        board.setGrid(playerNumber);
    }

    /**
     *
     * @param playerIndex Index of the player in List<player>.
     * @param n Row of the board to draw the item_tile.
     * @param m Column of the board to draw the item_tile.
     * @return True only if the draw is valid.
     */
    public boolean draw(int playerIndex, int n, int m){
        if(i>2){
            return false;
        }
        item temp=board.drawItem(n,m);
        if(temp!=item.EMPTY){
            player.get(playerIndex).bookshelf.itemToPut[i] = temp;
            i++;
            return true;
        }
        else{
            return false;
        }
    }

    /** STO MODIFICANDO **/
    public void placeItem(int i, int m,int a, int b, int c){
        player.get(i).bookshelf.putItems(m,a,b,c);
    }


    /** STO MODIFICANDO **/
    public String calculateScore(){
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
