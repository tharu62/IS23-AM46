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

public class CONTROLLER {
    public GAME game;
    public boolean lobbyIsReady = false;
    public boolean LobbyIsFull = false;
    public boolean GameIsOver = false;
    public List<ClientHandler> clientsTCP;
    public  List<GameClient> clientsRMI;
    public int players = 0;

    /************************************************ GETTER **********************************************************/
    public item[][] getBookshelf(String username){
        return game.space.player.get(game.search(username)).bookshelf.getGrid();
    }
    public item[][] getBoard(){
        return game.space.board.Grid;
    }
    public int getPersonalGoalCards(String username){
        int playerIndex = game.search(username);
        return game.space.player.get(playerIndex).personal.getCardLogic().getId();
    }
    public COMMON_GOAL_CARD getCommonGoalCard(int i){
        return game.master.FirstDraw.card.get(i);
    }
    public boolean getLobbyIsReady() { return lobbyIsReady; }
    public int getCurrentPlayers(){
        return players;
    }
    public boolean getLobbyIsFull(){
        return LobbyIsFull;
    }

    /*********************************************** SETTERS **********************************************************/

    public void setGame(GAME game){
        this.game = game;
    }

    public boolean setFirstLogin(String username, int LobbySize){
        if(LobbySize < 5 && LobbySize > 1) {
            game.LobbySize = LobbySize;
            game.addPlayer(username);
            lobbyIsReady = true;
            return true;
        }
        return false;
    }

    public boolean setLogin(String username){
        if(!getLobbyIsFull() && newUsername(username)) {
            if ( game.CurrentLobbySize == (game.LobbySize -1)) {
                game.addPlayer(username);
                game.setBoard();
                game.DrawCommonGoalCards();
                game.DrawPersonalGoalCards();
                game.ChooseFirstPlayerSeat();
                setTurn();
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

    public void setTurn(){
        game.masterStartTurn();
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

    /************************************************ PRIVATE *********************************************************/

    synchronized private boolean newUsername(String username){
        for(int i = 0; i < game.space.player.size() ; i++ ){
            if(game.space.player.get(i).getUsername().equals(username)){
                return false;
            }
        }
        return true;
    }

}


