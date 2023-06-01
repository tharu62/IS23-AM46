package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CONTROLLER{
    public String username;
    public int LobbySize;
    public item[][] grid;
    public item[][] bookshelf = {{item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,},
                                 {item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,item.OBJECT,}};

    public boolean firstToConnect = false , LoginOK = false, LobbyIsReady = false, myTurn = false, end_turn = false;
    public List<Draw> draw = new ArrayList<>(3);
    public int drawStatus = 0;
    public boolean reply_draw = false;
    public boolean draw_valid = false;
    public boolean draw_end = false;
    public int[] put = new int[4];
    public boolean reply_put = false;
    public boolean put_valid = false;
    public boolean put_end = false;
    public boolean reply_Personal = false;
    public int PersonalGoalCardID = -1;
    public List<Integer> cards = new ArrayList<>();
    public List<String> players = new ArrayList<>();
    public List<MESSAGE> chatBuffer = new ArrayList<>();
    public CLI cli;
    public COM com;

    public CONTROLLER(Connection connection , ClientRMI client) throws InterruptedException {
        if(connection == Connection.RMI){
            com = new RMI(client);
            cli = new CLI(this, client);
        }
    }

    public CONTROLLER(Connection connection, ClientTCP client) throws InterruptedException {
        if(connection == Connection.TCP){
            com = new TCP(client);
            cli = new CLI(this, client);
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

    synchronized public void setPlayerToPlay( String ptp ) throws RemoteException {
        if( this.username.toLowerCase().equals(ptp) ){
            setMyTurn(true);
        }
        else{
            notifyCLI(" IT SI NOT YOUR TURN ");
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

    synchronized public void receiveChat( MESSAGE message){
        if(myTurn) {
            if(!message.header[0].equals(username)){
                chatBuffer.add(message);
            }
        }else{
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

    synchronized public void setLastRound(){
        cli.cmd.notify("******************************************************************************************");
        cli.cmd.notify("                                        LAST ROUND                                        ");
        cli.cmd.notify("******************************************************************************************");
    }

    public void startCLI(){
        this.cli.start();
    }
}

//TODO spostare tutti i println() su view (CLI).