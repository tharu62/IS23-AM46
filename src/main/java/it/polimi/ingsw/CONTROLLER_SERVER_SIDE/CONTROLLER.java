package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.RMI.GameClient;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.CHAT;
import it.polimi.ingsw.TCP.ClientHandlerTCP;
import it.polimi.ingsw.TCP.Command;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CONTROLLER {
    public GAME game;
    public boolean lobbyIsReady = false;
    public boolean LobbyIsFull = false;
    public boolean GameIsOver = false;
    public int players = 0;
    public List<ClientHandlerTCP> clientsTCP;
    public Map<GameClient, String> clientsRMI = new HashMap<>();
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
            PLAYER player = new PLAYER();
            player.username = username;
            player.disconnected = false;
            playerList.add(player);
            return true;
        }
        return false;
    }

    public boolean setLogin(String username) throws RemoteException {
        if(lobbyIsReady){
            if(!getLobbyIsFull() && newUsername(username)) {
                if(game.CurrentLobbySize == (game.LobbySize -1)) {
                    LobbyIsFull = true;
                    game.addPlayer(username);
                    PLAYER player = new PLAYER();
                    player.username = username;
                    player.disconnected = false;
                    playerList.add(player);

                    game.setBoard();
                    game.DrawCommonGoalCards();
                    game.DrawPersonalGoalCards();
                    game.ChooseFirstPlayerSeat();
                    setTurn();
                    TurnUpdater updater = new TurnUpdater();
                    if(this.clientsRMI.size() > 0) {
                        updater.RMI(clientsRMI, getPlayerNames(), this, this.game);
                    }
                    if(clientsTCP.size() > 0) {
                        updater.TCP(clientsTCP, getPlayerNames(), this, this.game);
                    }
                    return true;
                } else {

                    game.addPlayer(username);
                    PLAYER player = new PLAYER();
                    player.username = username;
                    player.disconnected = false;
                    playerList.add(player);
                    return true;
                }
            }
        }
        return false;
    }

    synchronized public boolean setLoginReconnection(String username){
        if (!newUsername(username)) {
            int index = IndexSearch(username, playerList);
            if (index != -1) {
                if (playerList.get(index).disconnected) {
                    playerList.get(index).disconnected = false;
                    return true;
                }
            }
        }
        return false;
    }

    public void setTurn(){
        game.masterStartTurn();
        if(game.IsOver){
            if(this.clientsRMI.size() > 0) {
                for (GameClient gc : clientsRMI.keySet()) {
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

    public int getScore( String username ){
        return game.getScore(username);
    }

    public boolean setEndTurn( String username ) throws RemoteException {
        if(game.masterEndTurn(username)){
            while(turnOfDisconnectedPlayer(this.game.playerToPlay)){
                game.masterEndTurn(this.game.playerToPlay);
            }

            TurnUpdater updater = new TurnUpdater();
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
            for (GameClient gc : clientsRMI.keySet()) {
                gc.receiveMessage(message);
            }
        }
    }

    synchronized public void disconnected(String username) throws RemoteException {
        removeDisconnectedPlayerRMI(username);
        removeDisconnectedPlayerTCP(username);

        for(int i = 0; i < playerList.size(); i++){
            if (playerList.get(i).username.equals(username)) {
                playerList.get(i).disconnected = true;
                break;
            }
        }

        if(clientsTCP.size() > 0) {
            Command c = new Command();
            c.cmd = CMD.USER_DISCONNECTED;
            c.username = username;
            this.clientsTCP.get(0).broadcast(c);
        }

        if(this.clientsRMI.size() > 0) {
            for (GameClient gc : clientsRMI.keySet()) {
                gc.receiveDisconnectedPlayer(username);
            }
        }

        if(this.game.playerToPlay.equals(username)){
            game.forcedEndTurn(username);
            while(turnOfDisconnectedPlayer(this.game.playerToPlay)){
                game.masterEndTurn(this.game.playerToPlay);
            }

            TurnUpdater updater = new TurnUpdater();
            if(this.clientsRMI.size() > 0) {
                updater.RMI(clientsRMI, this, this.game);
            }
            if(clientsTCP.size() > 0) {
                updater.TCP(clientsTCP, this, this.game);
            }
        }
    }

    public void sendReconnectionData(String username){
        TurnUpdater turnUpdater = new TurnUpdater();

        for(GameClient gameClient : clientsRMI.keySet()){
            if(clientsRMI.get(gameClient).equals(username)){
                turnUpdater.reconnectRMI(gameClient, getPlayerNames(),this, game,username);
                break;
            }
        }

        for( ClientHandlerTCP clientHandler : clientsTCP){
            if(clientHandler.username.equals(username)){
                turnUpdater.reconnectTCP(clientHandler, getPlayerNames(),this, game);
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

    synchronized private void removeDisconnectedPlayerTCP(String username){
        for(int i = 0; i < clientsTCP.size(); i++){
            if(clientsTCP.get(i).username.equals(username)){
                clientsTCP.remove(i);
                break;
            }
        }
    }

    synchronized private void removeDisconnectedPlayerRMI(String username){
        for(GameClient gameClient : clientsRMI.keySet()){
            if(clientsRMI.get(gameClient).equals(username)){
                clientsRMI.remove(gameClient);
                break;
            }
        }
    }

    synchronized private boolean turnOfDisconnectedPlayer(String username){
        for (PLAYER player : playerList) {
            if (player.disconnected) {
                if(player.username.equals(username)) {
                    return true;
                }
            }
        }
        return false;
    }

}


