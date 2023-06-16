package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.VIEW.GUI.GUI;

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
    public boolean bookshelf_received = false;
    public int PersonalGoalCardID = -1;
    public List<Integer> cards = new ArrayList<>();
    public List<Integer> token_value = new ArrayList<>();
    public List<String> players = new ArrayList<>();
    public List<MESSAGE> chatBuffer = new ArrayList<>();
    public GameInterface Interface;
    public CLI cli;
    public GUI gui;

    public CONTROLLER(Connection connection , ClientRMI client , interfaceType Interface) throws InterruptedException {
        if(connection == Connection.RMI){
            if(Interface == interfaceType.GUI){
                gui = new GUI();
                this.Interface = new guiHandler(this.gui);
            }
            if(Interface == interfaceType.CLI ){
                cli  = new CLI(this, client);
                this.Interface = new cliHandler(this.cli);
            }
        }
    }

    public CONTROLLER(Connection connection, ClientTCP client, interfaceType Interface) throws InterruptedException {
        if(connection == Connection.TCP){
            if(Interface == interfaceType.GUI){
                gui = new GUI();
                this.Interface = new guiHandler(this.gui);
            }
            if(Interface == interfaceType.CLI){
                cli = new CLI(this, client);
                this.Interface = new cliHandler(this.cli);
            }
        }
    }

    /******************************************************************************************************************/
    public String getUsername(){
        return Interface.getUsername(this);
    }
    public int getLobbySize(){
        return Interface.getLobbySize(this);
    }
    synchronized public boolean getMyTurn(){
        return this.myTurn;
    }
    synchronized public boolean getReplyPut(){
        return this.reply_put;
    }
    synchronized public boolean getReplyDraw() { return this.reply_draw; }
    synchronized public boolean getReplyPersonal(){
        return this.reply_Personal;
    }
    synchronized public boolean getReplyBookshelf(){
        return this.bookshelf_received;
    }

    /******************************************************************************************************************/

    public void notifyInterface(String message){
        Interface.notifyInterface(message);
    }

    public void setLobbyIsReady(boolean bool){
        this.LobbyIsReady = bool;
    }

    public void setPlayerToPlay( String ptp ) {
        if( this.username.toLowerCase().equals(ptp) ){
            Interface.notifyInterface("                                 IT IS YOUR TURN                                          ");
            this.myTurn = true;
        }
        else{
            this.myTurn =false;
            Interface.notifyInterface("                                IT IS NOT YOUR TURN                                       ");
        }
    }

    public void setBoard( item[][] grid ){
        this.grid = grid;
    }

    public void setCommonGoals(int  cardID, int token){
        boolean setNotDone = true;
        if(cards.size() == 0){
            this.cards.add(cardID);
            this.token_value.add(token);
            setNotDone = false;
        }
        if(cards.size() == 1 && setNotDone){
            this.cards.add(cardID);
            this.token_value.add(token);
            setNotDone = false;
        }
        if(cards.size() == 2 && setNotDone){
            this.cards = new ArrayList<>();
            this.cards.add(cardID);
            this.token_value.add(token);
        }
    }

    public void setPersonalGoal(int cardID){
        this.PersonalGoalCardID = cardID;
        this.reply_Personal = true;
    }

    public void setBookshelf(item[][] bookshelf){
        this.bookshelf = bookshelf;
    }

    public void receiveChat( MESSAGE message){
        if(myTurn) {
            if(!message.header[0].equals(username)){
                chatBuffer.add(message);
            }
        }else{
            if(message.header[1].equals("everyone")) {
                if(!message.header[0].equals(username)){
                    Interface.notifyInterface(" NEW CHAT MESSAGE !");
                    Interface.notifyInterface(message.header[0] + " < public >:" + message.text);
                }
            }
            if(message.header[1].equals(username)){
                if(!message.header[0].equals(username)){
                    Interface.notifyInterface(" NEW CHAT MESSAGE !");
                    Interface.notifyInterface(message.header[0] + " < private > : " + message.text);
                }
            }
        }
    }

    public void setLastRound(){
        Interface.notifyInterface("                                        LAST ROUND                                        ");
    }

    public void startCLI(){
        Interface.startInterface();
    }

}

