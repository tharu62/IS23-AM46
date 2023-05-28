package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import com.google.gson.Gson;
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

public class CONTROLLER {
    public GAME game;
    public boolean GameHasNotStarted = true;
    public boolean TurnHasStarted = false;
    public boolean LobbyIsFull = false;
    public boolean GameIsOver = false;
    public Gson g = new Gson();
    public List<ClientHandler> clientsTCP;
    public  List<GameClient> clientsRMI;
    public int players = 0;
    public Object lock = new Object();

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

    synchronized public int getPersonalGoalCards(String username){
        int playerIndex = game.search(username);
        return game.space.player.get(playerIndex).personal.getCardLogic().getId();
    }

    synchronized public COMMON_GOAL_CARD getCommonGoalCard(int i){
        return game.master.FirstDraw.card.get(i);
    }

    synchronized public int getCurrentLobbySize(){
        return game.CurrentLobbySize;
    }
    synchronized public boolean getLobbyIsFull(){
        return LobbyIsFull;
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
            synchronized (lock) {
                lock.notifyAll();
            }
            return true;
        }
        return false;
    }

    synchronized public boolean setLogin(String username){
        if(!getLobbyIsFull() && newUsername(username)) {
            if ( game.CurrentLobbySize == (game.LobbySize -1)) {
                game.addPlayer(username);
                game.setBoard();
                game.DrawCommonGoalCards();
                game.DrawPersonalGoalCards();
                game.ChooseFirstPlayerSeat();
                LobbyIsFull = true;

                if(this.clientsRMI.size() > 0) {
                    for (GameClient gc : clientsRMI) {
                        try {
                            gc.receiveBoard(this.getBoard());
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    for (GameClient gc : clientsRMI) {
                        try {
                            gc.receiveCommonGoals(this.getCommonGoalCard(0).getCardLogic().getId());
                            gc.receiveCommonGoals(this.getCommonGoalCard(1).getCardLogic().getId());
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
                    temp.broadcast.cardsID = new ArrayList<>();
                    temp.cmd = CMD.COMMON_GOALS;
                    temp.broadcast.cardsID.add(getCommonGoalCard(0).getCardLogic().getId());
                    temp.broadcast.cardsID.add(getCommonGoalCard(1).getCardLogic().getId());
                    clientsTCP.get(0).broadcast(temp);

                    temp = new Command();
                    temp.broadcast = new BROADCAST();
                    temp.cmd = CMD.PLAYER_TO_PLAY;
                    temp.broadcast.ptp = game.playerToPlay;
                    clientsTCP.get(0).broadcast(temp);
                }
                return true;
            } else {
                game.addPlayer(username);
                return true;
            }
        }
        return false;
    }

    public boolean setTurn(String username){
        boolean turn = game.masterStartTurn(username);
        if(game.IsOver){
            if(this.clientsRMI.size() > 0) {
                for (GameClient gc : clientsRMI) {
                    try {
                        gc.receiveWinner(game.space.winner);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(clientsTCP.size() > 0) {
                Command temp = new Command();
                temp.cmd = CMD.WINNER;
                temp.username = game.space.winner;
                clientsTCP.get(0).broadcast(temp);
            }
        }
        return turn;
    }

    public boolean setDraw(String username, int n, int m){
        return game.playerDrawItem(username, n, m);
    }

    public boolean setBookshelf(String username, int m, int a, int b, int c){
        return game.playerPutItems( username, m, a, b, c);
    }

    public int setScore( String username ){
        game.PlayerWantsToCheckScore(username);
        PLAYER player= (PLAYER) game.space.player.stream().filter(x -> x.getUsername().equals(username));
        return player.score;
    }

    public boolean setEndTurn( String username ){
        if(game.masterEndTurn(username)){
            if(this.clientsRMI.size() > 0) {
                if(game.master.round.last){
                    for (GameClient gc : clientsRMI) {
                        try {
                            gc.receiveLastRound();
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                for (GameClient gc : clientsRMI) {
                    try {
                        gc.receiveBoard(this.getBoard());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

                for (GameClient gc : clientsRMI) {
                    try {
                        gc.receiveCommonGoals(this.getCommonGoalCard(0).getCardLogic().getId());
                        gc.receiveCommonGoals(this.getCommonGoalCard(1).getCardLogic().getId());
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
                if(game.master.round.last){
                    Command temp = new Command();
                    temp.cmd = CMD.LAST_ROUND;
                    clientsTCP.get(0).broadcast(temp);
                }
                Command temp = new Command();
                temp.cmd = CMD.BOARD;
                temp.broadcast = new BROADCAST();
                temp.broadcast.grid = new item[9][9];
                temp.broadcast.grid = getBoard();
                clientsTCP.get(0).broadcast(temp);

                temp = new Command();
                temp.broadcast = new BROADCAST();
                temp.broadcast.cardsID = new ArrayList<>();
                temp.cmd = CMD.COMMON_GOALS;
                temp.broadcast.cardsID.add(getCommonGoalCard(0).getCardLogic().getId());
                temp.broadcast.cardsID.add(getCommonGoalCard(1).getCardLogic().getId());
                clientsTCP.get(0).broadcast(temp);

                temp = new Command();
                temp.broadcast = new BROADCAST();
                temp.cmd = CMD.PLAYER_TO_PLAY;
                temp.broadcast.ptp = game.playerToPlay;
                clientsTCP.get(0).broadcast(temp);
            }
        }
        return false;
    }

    public void setChat(MESSAGE message) throws RemoteException {
        game.chat.addMessage(message);
        if(clientsTCP.size() > 0) {
            Command c = new Command();
            c.cmd = CMD.FROM_SERVER_CHAT;
            c.chat = new CHAT();
            c.chat.message = new MESSAGE();
            c.chat.message = message;
            this.clientsTCP.get(0).broadcast(c);
        }
        if(this.clientsRMI.size() > 0) {
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

}


