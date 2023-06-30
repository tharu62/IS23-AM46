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
    public int players = 0;
    public final Object lock = new Object();
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
    synchronized public int getCurrentPlayers(){
        return players;
    }
    synchronized public boolean getLobbyIsFull(){
        return LobbyIsFull;
    }
    public int getScore( String username ){
        return game.getScore(username);
    }

    /*********************************************** SETTERS **********************************************************/

    public void setGame(GAME game){
        this.game = game;
    }

    /**
     * This method sets the login for the first client to connect to the server.
     * @param username: the client's username
     * @param LobbySize: the number of players for the game
     * @return true if the username is not already in use and if the lobbysize is between 2 and 4
     */
    public boolean setFirstLogin(String username, int LobbySize){
        if(LobbySize < 5 && LobbySize > 1 && newUsername(username) && !username.equals("")) {
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

    /**
     * This method adds a player to the game. When the lobby is full it sets up the game
     * @param username: the client's username
     * @return true if the username is not already in use and if the lobby is not full
     */
    public boolean setLogin(String username) throws RemoteException {
        if(lobbyIsReady){
            if(!getLobbyIsFull() && newUsername(username) && !username.equals("")) {
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
                } else {

                    game.addPlayer(username);
                    PLAYER player = new PLAYER();
                    player.username = username;
                    player.disconnected = false;
                    playerList.add(player);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * This method reconnects a client that was disconnected
     * @param username: the username of a client that was disconnected
     * @return true if the username was already chosen and if the client with that username was disconnected
     */
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

    /**
     * This method calls the method masterStartTurn() of the GAME class. If the game is over it sends to all the clients
     * the winner of this game
     */
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

    /**
     * This method sets the draw made by the clients.
     * @param username: the username of the client who made the draw
     * @param n: the row in which the drawn item is located on the board
     * @param m: the column in which the drawn item is located on the board
     * @return true if the player draw is valid
     */
    public boolean setDraw(String username, int n, int m){
        return game.playerDrawItem(username, n, m);
    }

    /**
     * This method puts in the bookshelf the items drawn by the client
     * @param username: the username of the client who made the put
     * @param m: the column in which the drawn items will be placed
     * @param a: it's the order of the first item to put in the bookshelf
     * @param b: it's the order of the second item to put in the bookshelf
     * @param c: it's the order of the third item to put in the bookshelf
     * @return true if the put is valid
     */
    public boolean setBookshelf(String username, int m, int a, int b, int c){
        return game.playerPutItems( username, m, a, b, c);
    }

    /**
     * This method ends the turn of the player and sets the next turn. If the game is over it sends to all the clients connected
     * their personal score and the winner of the game
     * @param username: the client's username
     */
    public void setEndTurn( String username ) throws RemoteException {
        if(game.masterEndTurn(username)){
            if(game.IsOver){

                TurnUpdater updater = new TurnUpdater();
                if (this.clientsRMI.size() > 0) {
                    updater.RMI_last(clientsRMI, this, this.game);
                }
                if (clientsTCP.size() > 0) {
                    updater.TCP_last(clientsTCP, this, this.game);
                }

            }else {
                while (turnOfDisconnectedPlayer(this.game.playerToPlay)) {
                    game.masterEndTurn(this.game.playerToPlay);
                }

                TurnUpdater updater = new TurnUpdater();
                if (this.clientsRMI.size() > 0) {
                    updater.RMI(clientsRMI, this, this.game);
                }
                if (clientsTCP.size() > 0) {
                    updater.TCP(clientsTCP, this, this.game);
                }
            }
        }
    }

    /**
     * This method sends the message to a specific client (if the message is private) or to every client connected
     * @param message: the message written by a client
     */
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

    /**
     * This method removes a client who logged out, notifies the other clients connected and sets the next turn.
     * If there is only one player left, the game ends and that player is the winner; if a client disconnects while it's his turn,
     * the turn ends.
     * @param username: the username of the client that logged out
     */
    synchronized public void disconnected(String username) throws RemoteException {
        removeDisconnectedPlayerRMI(username);
        removeDisconnectedPlayerTCP(username);

        for (PLAYER player : playerList) {
            if (player.username.equals(username)) {
                player.disconnected = true;
                break;
            }
        }
        if(OnlyOnePlayerOnline()){
            game.IsOver = true;
            game.space.winner = onlyPlayerOnline();
            TurnUpdater updater = new TurnUpdater();
            if (this.clientsRMI.size() > 0) {
                updater.RMI_last(clientsRMI, this, this.game);
            }
            if (clientsTCP.size() > 0) {
                updater.TCP_last(clientsTCP, this, this.game);
            }
        }else {

            if (clientsTCP.size() > 0) {
                Command c = new Command();
                c.cmd = CMD.USER_DISCONNECTED;
                c.username = username;
                this.clientsTCP.get(0).broadcast(c);
            }

            if (this.clientsRMI.size() > 0) {
                for (GameClient gc : clientsRMI.keySet()) {
                    if (!clientsRMI.get(gc).equals(username)) {
                        System.out.println(username);
                        System.out.println(clientsRMI.get(gc));
                        gc.receiveDisconnectedPlayer(username);
                    }
                }
            }

            if (this.game.playerToPlay.equals(username)) {
                game.forcedEndTurn(username);
                while (turnOfDisconnectedPlayer(this.game.playerToPlay)) {
                    game.masterEndTurn(this.game.playerToPlay);
                }

                TurnUpdater updater = new TurnUpdater();
                if (this.clientsRMI.size() > 0) {
                    updater.RMI(clientsRMI, this, this.game);
                }
                if (clientsTCP.size() > 0) {
                    updater.TCP(clientsTCP, this, this.game);
                }
            }
        }
    }

    /**
     * This method reconnects a client that was disconnected and allows that client to play
     * @param username: the username of the client that was disconnected
     */
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

    /**
     * This method checks if the username is new
     * @param username: the client's username
     * @return true if the username is new
     */
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

    /**
     * This method searches the index of the username in a list
     * @param username: the client's username
     * @param playerList: the list of the players in the game
     * @return the index of the username in the playerList, it returns -1 if the username is not in the playerList
     */
    synchronized private int IndexSearch(String username, List<PLAYER> playerList){
        for(int i=0; i<playerList.size(); i++){
            if(playerList.get(i).username.equals(username)){
                return i;
            }
        }
        return -1;
    }

    /**
     * This method removes the TCP player that logged out
     * @param username: the username of the TCP client that logged out
     */
    synchronized private void removeDisconnectedPlayerTCP(String username){
        for(int i = 0; i < clientsTCP.size(); i++){
            if(clientsTCP.get(i).username.equals(username)){
                clientsTCP.remove(i);
                break;
            }
        }
    }

    /**
     * This method removes the RMI player that logged out
     * @param username: the username of the RMI client that logged out
     */
    synchronized private void removeDisconnectedPlayerRMI(String username){
            for(GameClient gameClient : clientsRMI.keySet()){
                if(clientsRMI.get(gameClient).equals(username)){
                    clientsRMI.remove(gameClient);
                    break;
                }
            }
    }

    /**
     * This method check if it's the turn of a disconnected player
     * @param username: the client's username
     * @return true if the player with that username is disconnected
     */
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

    /**
     * This method checks how many clients are still in the game
     * @return true if only one client is still connected
     */
    private boolean OnlyOnePlayerOnline(){
        int counter = 0;
        for(PLAYER player : playerList){
            if(!player.disconnected){
                counter++;
                if(counter > 1){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method returns the player's username that is still connected
     * @return null if there isn't a player connected
     */
    private String onlyPlayerOnline(){
        for(PLAYER player : playerList){
            if(!player.disconnected){
                return player.username;
            }
        }
        return null;
    }
}


