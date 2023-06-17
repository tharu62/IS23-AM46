package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.TCP.CMD;
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
    public List<PLAYER> playerList = new ArrayList<>();

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
        if(LobbySize < 5 && LobbySize > 1 && newUsername(username)) {
            game.LobbySize = LobbySize;
            game.addPlayer(username);
            lobbyIsReady = true;
            return true;
        }
        return false;
    }

    public boolean setLogin(String username) throws RemoteException {
        if(!getLobbyIsFull() && newUsername(username)) {
            if ( game.CurrentLobbySize == (game.LobbySize -1)) {
                game.addPlayer(username);
                game.setBoard();
                game.DrawCommonGoalCards();
                game.DrawPersonalGoalCards();
                game.ChooseFirstPlayerSeat();
                setTurn();
                TurnUpdater updater = new TurnUpdater();
                LobbyIsFull = true;
                if(this.clientsRMI.size() > 0) {
                   updater.RMI(clientsRMI, getPlayerNames(), this, this.game);
                }
                if(clientsTCP.size() > 0) {
                   updater.TCP(clientsTCP, getPlayerNames(), this, this.game);
                }
                return true;
            } else {
                game.addPlayer(username);
                return true;
            }
        }
        return false;
    }

    synchronized public boolean setLoginReconnection(String username){
        //TODO
        // if(newUsername(username)){
        //      return false;
        // }else{
        //      Int index = IndexSearch(username, playerList);
        //      if(index != -1){
        //          if(playerList.get(index).disconnected){
        //              playerList.get(index).disconnected = false;
        //              return true;
        //          }
        //      }
        //      return false;
        // }
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
        it.polimi.ingsw.MODEL.PLAYER player = (it.polimi.ingsw.MODEL.PLAYER) game.space.player.stream().filter(x -> x.getUsername().equals(username));
        return player.score;
    }

    public boolean setEndTurn( String username ) throws RemoteException {
        if(game.masterEndTurn(username)){
            TurnUpdater updater = new TurnUpdater();

            //DA MODIFICARE PER RESILIENZA ALLE DISCONNESSIONI
            if(this.clientsRMI.size() > 0) {
                updater.RMI(clientsRMI, this, this.game);
            }
            if(clientsTCP.size() > 0) {
                updater.TCP(clientsTCP, this, this.game);
            }


        }
        return true;
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

    public void disconnected(String username) throws RemoteException {
        if(clientsTCP.size() > 0) {
            Command c = new Command();
            c.cmd = CMD.USER_DISCONNECTED;
            c.username = username;
            if(game.space.player.get(0).getUsername().equals(username)){
                this.clientsTCP.get(1).broadcast(c);
            }
            this.clientsTCP.get(0).broadcast(c);
        }
        if(this.clientsRMI.size() > 0) {
            for (GameClient gc : clientsRMI) {
                gc.receiveLOG(username);
            }
        }
        System.exit(0);
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

    synchronized private List<String> getPlayerNames(){
        List<String> playerNames = new ArrayList<>();
        for(int i = 0; i < game.space.player.size(); i++){
            playerNames.add(game.space.player.get(i).getUsername());
        }
        return playerNames;
    }

    synchronized private int IndexSearch(String username, List<PLAYER> playerList){
        for(int i=0; i<playerList.size(); i++){
            if(playerList.get(i).username.equals(username)){
                return i;
            }
        }
        return -1;
    }

}


