package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.*;

public class CONTROLLER {
    public boolean last=false;
    public boolean LobbyIsNotFull=true;
    GAME game;


    /******************************************************* GETTERS **************************************************/

    public item[][] getBookshelf(String username){
        PLAYER player = (PLAYER) game.space.player.stream().filter(x -> x.getUsername().equals(username));
        if(player==null){
            /** yet to understand CONTROLLER behavior if non-legal command is given **/
            return new item[0][];
        }else {
            return player.bookshelf.getGrid();
        }
    }

    public item[][] getBoard(){
        return game.space.board.Grid;
    }

    public PERSONAL_GOALS getPersonalGoalCards(String username){
        PLAYER player= (PLAYER) game.space.player.stream().filter(x -> x.getUsername().equals(username));
        return player.personal;
    }

    public COMMON_GOALS getCommonGoalCard(String username){
        return game.master.FirstDraw.card;
    }

    public void get(String username){

    }

    /****************************************************** SETTERS ***************************************************/

    public void setGame(GAME game){
        this.game=game;
    }

    public void setUsername(String username){
        if(LobbyIsNotFull) {
            if (last) {
                game.addPlayer(username);
                game.setBoard();
                game.DrawPersonalGoalCards();
                game.DrawCommonGoalCards();
                LobbyIsNotFull = false;
            } else {
                if(newUsername(username)){
                    last = game.addPlayer(username);
                }
            }
        }
    }

    public void setTurn(String username){
        game.masterStartTurn(username);
    }

    public boolean setDraw(String username, int n, int m){
        return game.playerDrawItem(username, n, m);
    }

    public void setBookshelf(String username, int m, int a, int b, int c){
        game.playerPutItems( username, m, a, b, c);       /** return boolean **/
    }

    public void set(String username){

    }



    /******************************************************************************************************************/

    private boolean newUsername(String username){
        return true; /** yet to code **/
    }
}


