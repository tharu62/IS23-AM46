package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.BROADCAST;
import it.polimi.ingsw.TCP.COMANDS.CHAT;
import it.polimi.ingsw.TCP.ClientHandler;
import it.polimi.ingsw.TCP.Command;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CONTROLLER extends Thread {
    public GAME game;
    public boolean GameHasNotStarted = true;
    public boolean TurnHasStarted = false;
    public boolean LobbyIsFull = false;
    public boolean GameIsOver = false;
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

    synchronized public COMMON_GOAL_CARD getCommonGoalCard(int i){
        return game.master.FirstDraw.card.get(i);
    }

    synchronized public int getCurrentLobbySize(){
        return game.CurrentLobbySize;
    }
    synchronized public boolean getLobbyIsFull(){
        return this.LobbyIsFull;
    }

    /*********************************************** SETTERS **********************************************************/

    synchronized public void setGame(GAME game){
        this.game = game;
    }

    //TODO caso in cui più player accedono contemporaneamente.
    synchronized public boolean setFirstLogin(String username, int LobbySize){
        if(LobbySize < 5 && LobbySize > 1) {
            game.LobbySize = LobbySize;
            game.addPlayer(username);
            return true;
        }
        return false;
    }

    synchronized public boolean setLogin(String username){
        if(!LobbyIsFull && newUsername(username)) {
            if ( game.CurrentLobbySize == (game.LobbySize -1)) {
                game.addPlayer(username);
                game.setBoard();
                game.DrawCommonGoalCards();
                game.DrawPersonalGoalCards();
                this.LobbyIsFull = true;
                return true;
            } else {
                game.addPlayer(username);
                return true;
            }
        }
        return false;
    }

    synchronized public boolean setTurn(String username){
        if(username.equals(game.playerToPlay)){
            if(game.masterStartTurn(username) && !TurnHasStarted){
                this.TurnHasStarted = true;
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

    synchronized public void setChat(MESSAGE message) throws RemoteException {
        game.chat.addMessage(message);
        if(message.header[1].equals("everyone")) {
            Command c = new Command();
            c.chat = new CHAT();
            c.chat.message = new MESSAGE();
            c.chat.message = message;
            c.chat.username = message.header[0];
            this.clientsTCP.get(0).broadcast(c);
            for (GameClient gc : clientsRMI) {
                gc.receiveMessage(message);
            }
        }else{
            Command c = new Command();
            c.chat = new CHAT();
            c.cmd = CMD.FROM_SERVER_CHAT;
            c.chat.message = new MESSAGE();
            c.chat.message = message;
            c.chat.username = message.header[0];
            List<ClientHandler> receiver = this.clientsTCP
                    .stream()
                    .filter(a -> a.username.equals(message.header[1]))
                    .toList();
            receiver.get(0).CommandSwitcher(c); //TODO case receiver has no element? (to consider input management from client)
            for (GameClient gc : clientsRMI) {
                gc.receiveMessage(message);
            }
        }
    }

    /******************************************************************************************************************/

    synchronized private boolean newUsername(String username){
        //TODO
        return true;
    }

    /******************************************************************************************************************/

    @Override
    public void run()  {
        System.out.println(" Controller ready ");
        while (true) {

            /** PHASE 1
             *   After the login phase the Server sends the Board, the Common_goal_cards and player_to_play;
             */
            if ( getLobbyIsFull() && this.GameHasNotStarted ) {

                if(clientsRMI.size() > 0) {
                    for (GameClient gc : clientsRMI) {
                        try {
                            gc.receiveBoard(this.getBoard());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    for (GameClient gc : clientsRMI) {
                        try {
                            gc.receiveCommonGoals(this.getCommonGoalCard(0));
                            gc.receiveCommonGoals(this.getCommonGoalCard(1));
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    for (GameClient gc : clientsRMI) {
                        try {
                            gc.receivePlayerToPlay(game.playerToPlay);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if(clientsTCP.size() > 0) {
                    Command temp = new Command();
                    temp.cmd = CMD.BOARD;
                    temp.broadcast = new BROADCAST();
                    temp.broadcast.grid = new item[9][9];
                    temp.broadcast.grid = getBoard();
                    clientsTCP.get(0).broadcast(temp);

                    temp = new Command();
                    temp.broadcast = new BROADCAST();
                    temp.broadcast.cards = new ArrayList<>();
                    temp.cmd = CMD.COMMON_GOALS;
                    temp.broadcast.cards.add(getCommonGoalCard(0));
                    temp.broadcast.cards.add(getCommonGoalCard(1));
                    clientsTCP.get(0).broadcast(temp);

                    temp = new Command();
                    temp.broadcast = new BROADCAST();
                    temp.cmd = CMD.PLAYER_TO_PLAY;
                    temp.broadcast.ptp = game.playerToPlay;
                    clientsTCP.get(0).broadcast(temp);
                }

                GameHasNotStarted = false;
            }

            /**
             * PHASE 2
             */
            if (TurnHasStarted) {

            }

            /** PHASE 3
             * If it's the last round and the last turn, the game is over, the model autonomously calculate the scores
             * and finds the winner.
             */
            if (game.master.round.last && game.master.round.turn.count == game.LobbySize) {
                GameIsOver = true;
                Command temp = new Command();
                temp.cmd = CMD.WINNER;
                temp.broadcast.ptp = this.game.space.winner;
                clientsTCP.get(0).broadcast(temp);
                for (GameClient gc : clientsRMI) {
                    try {
                        gc.receiveWinner( this.game.space.winner );
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

    }


}


