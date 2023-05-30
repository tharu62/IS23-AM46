package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.TCP.Command;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;
//import it.polimi.ingsw.VIEW.GUI.GUI;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CONTROLLER{
    public String username;
    public boolean firstToConnect = false;
    public boolean LoginOK = false;
    public boolean LobbyIsReady = false;
    public boolean need_to_reconnect = false;
    public boolean GameHasStarted = false;
    public int LobbySize;
    public item[][] grid;
    public item[][] bookshelf = {{item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,}};
    public List<Draw> draw = new ArrayList<>(3);
    public int drawStatus = 0;
    public int[] put = new int[4];
    public boolean myTurn = false;
    public boolean reply_draw = false;
    public boolean draw_valid = false;
    public boolean reply_put = false;
    public boolean put_valid = false;
    public boolean reply_Personal = false;
    public int PersonalGoalCardID = -1;
    public List<String> players = new ArrayList<>();
    public List<Integer> cards = new ArrayList<>();
    public CLI cli = new CLI(this);
    public boolean draw_end = false;
    public boolean end_turn = false;
    public boolean put_end = false;
    public COM com;
    //public GUI gui = new GUI();

    public CONTROLLER(Connection connection , ClientRMI client) throws InterruptedException {
        if(connection == Connection.RMI){
            com = new RMI(client);
        }
    }

    public CONTROLLER(Connection connection, ClientTCP client) throws InterruptedException {
        if(connection == Connection.TCP){
            com = new TCP(client);
        }
    }

    /******************************************************************************************************************/
    synchronized public void notifyCLI(String message){
        cli.cmd.notify(message);
    }
    synchronized public String getUsername(){
        this.username = cli.cmd.getUsername();
        return this.username;
    }

    synchronized public int getPersonalGoal() throws RemoteException {
        if(PersonalGoalCardID != -1 ){
            return PersonalGoalCardID;
        }
        return com.getPersonalGoal(PersonalGoalCardID, this.username, this.cli);
    }

    synchronized public int getLobbySize(){
        this.LobbySize = cli.cmd.getLobbySize();
        return this.LobbySize;
    }

    synchronized public boolean getNeedToReconnect(){
        return need_to_reconnect;
    }

    synchronized public boolean getLobbyIsReady(){
        return LobbyIsReady;
    }

    synchronized public boolean getMyTurn(){
        return myTurn;
    }

    /******************************************************************************************************************/

    synchronized public void setLobbyIsReady(boolean bool){
        this.LobbyIsReady = bool;
    }

    synchronized public void setMyTurn(boolean bool){
        this.myTurn = bool;
    }

    //synchronized public void connect() throws RemoteException {
        //if(connection == Connection.TCP){
          //  Command c = new Command();
          // c.cmd = CMD.ASK_LOBBY_READY;
          //  String askLobby = clientTCP.g.toJson(c);
          //  clientTCP.out_ref.println(askLobby);
        //}
        //if(connection == Connection.RMI){
          //  ClientRMI.gs.askLobbyReady( clientRMI );
        //}
    //}

    synchronized public void setPlayerToPlay( String ptp ) throws RemoteException {
        if( this.username.toLowerCase().equals(ptp) ){
            com.setPlayerToPlay(this.username, this.myTurn);
        }
    }

    synchronized public void setBoard( item[][] grid ){
        this.grid = grid;
        cli.cmd.printBoard(grid);
    }

    synchronized public void setCommonGoals(int  cardID){
        boolean setNotDone = true;
        if(cards.size() == 0){
            cards.add(cardID);
            setNotDone = false;
        }
        if(cards.size() == 1 && setNotDone){
            cards.add(cardID);
            cli.cmd.printCommonGoals(cards);
            GameHasStarted = true;
            setNotDone = false;
        }
        if(cards.size() == 2 && setNotDone){
            cards = new ArrayList<>();
            cards.add(cardID);
        }
    }

    synchronized public void setPersonalGoal(int cardID){
        cli.cmd.printPersonalGoal(cardID);
    }

    synchronized public void sendChat(String text, String receiver) throws RemoteException {
       com.sendChat(this.username, text , receiver);
    }

    synchronized public void receiveChat( MESSAGE message){
        if(!myTurn) {
            if(message.header[1].equals("everyone")) {
                if(!message.header[0].equals(username)){
                    notifyCLI(" NEW CHAT MESSAGE !");
                    notifyCLI(message.header[0] + ":" + message.text);
                }
            }
            if(message.header[1].equals(username)){
                if(!message.header[0].equals(username)){
                    notifyCLI(" NEW CHAT MESSAGE !");
                    notifyCLI(message.header[0] + " ( PRIVATE ) : " + message.text);
                }
            }
        }
    }

    public boolean setDraw( int row, int col) throws RemoteException{
        return com.draw(this.username, row , col);
    }

    //TODO
    public boolean setPut(int a, int b, int c ,int col) throws RemoteException, InterruptedException {
        return com.put(this.username, a, b, c, col,  this.put_valid);
    }

    synchronized public void setBookshelf() throws RemoteException {
        com.bookshelf( this.cli , this.username);
    }

    synchronized public void endTurn() throws RemoteException {
        draw_end = false;
        put_end = false;
        end_turn = false;
        com.endTurn(this.myTurn, this.username);
    }

    synchronized public void setLastRound(){
        cli.cmd.notify("******************************************************************************************");
        cli.cmd.notify(" LAST ROUND!!! ");
        cli.cmd.notify("******************************************************************************************");
    }

    public void startCLI(){
        this.cli.start();
    }
}

