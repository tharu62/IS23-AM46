package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.BROADCAST;
import it.polimi.ingsw.TCP.ClientHandler;
import it.polimi.ingsw.TCP.Command;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CONTROLLER extends Thread{
    public GAME game;
    public boolean GameHasStarted = false;
    public boolean TurnHasStarted = false;
    public boolean LobbyIsFull = false;
    public boolean GameIsOver = false;
    public boolean last = false;
    public int connected_players = 0 ;
    public List<ClientHandler> clientsTCP;
    public  List<GameClient> clientsRMI;

    /************************************************ GETTER **********************************************************/

    synchronized public item[][] getBookshelf(String username){
        int playerIndex = game.search(username);
        if(game.space.player == null){
            // yet to understand CONTROLLER behavior if non-legal command is given
            return new item[0][];
        }else {
            return game.space.player.get(playerIndex).bookshelf.getGrid();
        }
    }
    synchronized public item[][] getBoard(){
        return game.space.board.Grid;
    }

    synchronized public PERSONAL_GOAL_CARD getPersonalGoalCards(String username){
        int playerIndex = game.search(username);
        return game.space.player.get(playerIndex).personal;
    }

    synchronized public List<COMMON_GOAL_CARD> getCommonGoalCard(){
        return game.master.FirstDraw.card;
    }

    /*********************************************** SETTERS **********************************************************/

    synchronized public void setGame(GAME game){
        this.game=game;
    }

    synchronized public boolean setFirstLogin(String username, int LobbySize){
        if(LobbySize<=4 && LobbySize>=2) {
            game.addPlayer(username);
            game.LobbySize = LobbySize;
            this.connected_players = game.playerNumber;
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
                this.connected_players = game.playerNumber;
                return true;
            } else {
                if(newUsername(username)){
                    last = game.addPlayer(username);
                    this.connected_players = game.playerNumber;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    synchronized public boolean setTurn(String username){
        if(username.equals(game.playerToPlay)){
            if(game.masterStartTurn(username) && !TurnHasStarted){
                TurnHasStarted = true;
                return true;
            }else{
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
        if(game.masterEndTurn(username)){
            TurnHasStarted = false;
            return true;
        }
        return false;
    }

    synchronized public void setChat(String username, MESSAGE message){
        game.chat.addMessage(message);
    }

    /******************************************************************************************************************/

    synchronized private boolean newUsername(String username){
        //TODO
        return true;
    }

    /******************************************************************************************************************/

    @Override
    public void run() {
        System.out.println(" Controller ready ");
        while (true) {

            /** PHASE 1
             *   After the login phase the Server sends the Board, the Common_goal_cards and player_to_play.
             */
            if (game.master.round.turn.count == 0 && LobbyIsFull && !GameHasStarted) {
                Command temp;
                for (GameClient gc : clientsRMI) {
                    try {
                        gc.receiveBoard(getBoard());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        gc.receiveCommonGoals(getCommonGoalCard());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        gc.receivePlayerToPlay(game.playerToPlay);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

                temp = new Command();
                temp.cmd = CMD.BOARD;
                temp.broadcast = new BROADCAST();
                temp.broadcast.grid = new item[9][9];
                temp.broadcast.grid = getBoard();
                clientsTCP.get(0).broadcast(temp);

                temp = new Command();
                temp.broadcast = new BROADCAST();
                temp.broadcast.cards = new ArrayList<>();
                temp.cmd = CMD.COMMON_GOALS;
                temp.broadcast.cards = getCommonGoalCard();
                clientsTCP.get(0).broadcast(temp);

                temp = new Command();
                temp.broadcast = new BROADCAST();
                temp.cmd = CMD.PLAYER_TO_PLAY;
                temp.broadcast.ptp = game.playerToPlay;
                clientsTCP.get(0).broadcast(temp);

                GameHasStarted = true;
            }

            /**
             * PHASE 2
             */
            if (TurnHasStarted) {
                game.master.round.turn.count=game.master.round.turn.count+1;

            }

            /** PHASE 3
             * If it's the last round and the last turn, the game is over, the model autonomously calculate the scores
             * and finds the winner.
             */
            if (game.master.round.last && game.master.round.turn.count == (game.playerNumber - 1)) {
                GameIsOver = true;
                //TODO
                // temp = new Command();
                // temp.cmd = "WINNER";
                // temp.broadcast.ptp = controller.game.space.winner
                // clientsTCP.get(0).broadcast(temp);
                temp.cmd.score=temp.cmd.score+1;

            }
        }
    }

}


//sarebbe da mettere in una classe IsWinner.java

public class IsWinner extends GenericServerMessage {
    private final String winner;
    private final String condition;
    private final Board board;

    /**
     * Class constructor.
     *
     * @param winner        the nickname of the winner.
     * @param condition     the winning condition.
     * @param Board         the player's Board.
     */
    public IsWinner(String winner, String condition, Board board) {
        this.winner = winner;
        this.condition = condition;
        this.board = Board;
    }

    /**
     * Shows the message (CLI or GUI).
     *
     * @param view      a ViewInterface.
     */
    @Override
    public void show(ViewInterface view) {
        view.printBoard(Board);
        view.displayEndgameResult(winner, condition);
    }
}


