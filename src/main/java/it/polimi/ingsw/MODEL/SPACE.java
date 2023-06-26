package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class SPACE {
    public BOARD board= new BOARD();
    public List<PLAYER> player= new ArrayList<>(1);
    public String winner;
    int drawCounter = 0;

    /**
     *
     * @param playerNumber
     */
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
        if(drawCounter > 2){
            return false;
        }
        if(n == board.itemPos[0] && m == board.itemPos[1]){
            return false;
        }
        if(n == board.itemPos[2] && m == board.itemPos[3]){
            return false;
        }
        for(int i = 0; i < 5; i++){
            if(player.get(playerIndex).bookshelf.firstFreeRow(i) >= (drawCounter)){
                break;
            }
            if( i == 4 ){
                return false;
            }
        }
        item temp = board.drawItem(n,m);
        if(temp != item.EMPTY){
            player.get(playerIndex).bookshelf.itemToPut.add(temp);
            drawCounter++;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     *
     * @param i
     * @param m
     * @param a
     * @param b
     * @param c
     * @return
     */
    public boolean placeItem(int i, int m,int a, int b, int c){
        return player.get(i).bookshelf.putItems(m,a,b,c);
    }

    /**
     *
     * @return
     */
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

    /**
     * This method resets the board on the state it was before the player made his first draw.
     * @param playerIndex it's the player that made the last draw.
     */
    public void resetDraw( int playerIndex ){
        int row = 0;
        int col = 1;
        for(int j=0; j < player.get(playerIndex).bookshelf.itemToPut.size(); j++){
            board.Grid[board.itemPos[row]][board.itemPos[col]] = player.get(playerIndex).bookshelf.itemToPut.get(j);
            row +=2;
            col +=2;
        }
    }
}
