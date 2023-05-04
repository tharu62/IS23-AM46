package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.*;

import java.util.List;

public class CONTROLLER {
    public boolean last=false;
    public GAME game;
    public boolean accepted;
    public boolean LobbyIsFull=false;

    public boolean GameIsOver=false;
    int turn;

    /******************************************************* GETTERS **************************************************/

    public item[][] getBookshelf(String username){
        PLAYER player = (PLAYER) game.space.player.stream().filter(x -> x.getUsername().equals(username));
        if(player == null){
            // yet to understand CONTROLLER behavior if non-legal command is given
            return new item[0][];
        }else {
            return player.bookshelf.getGrid();
        }
    }
    public item[][] getBoard(){
        return game.space.board.Grid;
    }

    public PERSONAL_GOAL_CARD getPersonalGoalCards(String username){
        PLAYER player= (PLAYER) game.space.player.stream().filter(x -> x.getUsername().equals(username));
        return player.personal;
    }

    public List<COMMON_GOAL_CARD> getCommonGoalCard(){
        return game.master.FirstDraw.card;
    }

    public void getChat(String username){

    }

    /****************************************************** SETTERS ***************************************************/

    synchronized public void setGame(GAME game){
        this.game=game;
    }

    synchronized public boolean setFirstLogin(String username, int LobbySize){
        if(LobbySize<=4 && LobbySize>=2) {
            game.addPlayer(username);
            game.LobbySize=LobbySize;
            return true;
        }
        return false;
    }

    synchronized public boolean setLogin(String username){
        if(!LobbyIsFull) {
            if (last) {
                game.addPlayer(username);
                game.setBoard();
                game.DrawPersonalGoalCards();
                game.DrawCommonGoalCards();
                LobbyIsFull = true;
                return true;
            } else {
                if(newUsername(username)){
                    last = game.addPlayer(username);
                    return true;
                }
                return false;
            }
        }
        return false;
    }


    synchronized public boolean setTurn(String username){
        if(username.equals(game.playerToPlay)){
            if(game.masterStartTurn(username)){
                return true;
            }else{
                GameIsOver = true;
                return false;
            }

        }
        return false;
    }

    synchronized public boolean setDraw(String username, int n, int m){
        return game.playerDrawItem(username, n, m);
    }

    synchronized public boolean setBookshelf(String username, int m, int a, int b, int c){
        return game.playerPutItems( username, m, a, b, c);
    }

    synchronized public int setScore( String username ){
        game.PlayerWantsToCheckScore(username);
        PLAYER player= (PLAYER) game.space.player.stream().filter(x -> x.getUsername().equals(username));
        return player.score;
    }

    synchronized public boolean setEndTurn( String username ){
        return game.masterEndTurn(username);
    }
    synchronized public void setChat(String username, MESSAGE message){
        game.chat.addMessage(message);
    }

    /******************************************************************************************************************/

    private boolean newUsername(String username){
        return true; /** yet to code **/
    }
}


