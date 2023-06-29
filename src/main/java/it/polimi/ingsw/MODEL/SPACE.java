package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class SPACE {
    public BOARD board= new BOARD();
    public List<PLAYER> player = new ArrayList<>(1);
    public String winner;
    int drawCounter = 0;

    /**
     * This method initializes the Board with the method setGrid( ) of the Board.
     * @param playerNumber is the number of players in the game.
     */
    public void setBoard(int playerNumber){
        board.setGrid(playerNumber);
    }

    /**
     * This method checks if an item in the coordinates [n;m] of the Board can be drawn by checking if the number of
     * items is already saturated (max 3 item drawn each turn), if the item has already been drawn, if the item is
     * selectable by game logic and if the item it's not an empty object ( item already draw in other turns ).
     *
     * @param playerIndex Index of the player in List<player> player.
     * @param n is the Row of the board to draw the item_tile.
     * @param m is the Column of the board to draw the item_tile.
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
     * This method checks if the items in the draw pile of the player selected can be placed inside his bookshelf,
     * if it can then it completes the placing and return true else it returns false.
     *
     * @param i is the index of the player List who asked to place the items
     * @param m is the column in which to place the items
     * @param a is the position of the first item to place
     * @param b is the position od the second item to place
     * @param c is the position of the third item to place
     * @return true if the placing is successful or false if it's unsuccessful.
     */
    public boolean placeItem(int i, int m,int a, int b, int c){
        return player.get(i).bookshelf.putItems(m,a,b,c);
    }

    /**
     * This method calculate the score for each player in the game and finds the winner.
     * The score in calculated by summing up the personal goal card score, the common goal card score and the adjacent
     * item score. (the common goal card score is calculated in real time game while the personal goal and adjacent item
     * score is calculated on the spot)
     * After the score calculation the winner is return and stored in the winner attribute of the class.
     * @return a string of the username of the winner.
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
            if(tempScore <= player.get(i).score){
                tempScore=player.get(i).score;
                winner=i;
            }
        }
        this.winner = player.get(winner).username;
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
