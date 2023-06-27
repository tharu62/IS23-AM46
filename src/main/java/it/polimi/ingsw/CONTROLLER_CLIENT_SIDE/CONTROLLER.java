package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.NETWORK.Settings;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.VIEW.GUI.CommandsExecutor;
import it.polimi.ingsw.VIEW.GUI.GUI;

import java.io.IOException;
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
    public int PersonalGoalCardID;
    public List<Integer> cards = new ArrayList<>();
    public List<Integer> token_value = new ArrayList<>();
    public List<Draw> draw = new ArrayList<>(3);
    public int[] put = new int[4];
    public int score = 0;
    public boolean notConnected = true, firstToConnect = false , LoginOK = false, myTurn = false, end_turn = false;
    public int drawStatus = 0;
    public boolean reply_draw = false, draw_valid = false, draw_end = false;
    public boolean reply_put = false, put_valid = false;
    public boolean gameDataReceived = false;
    public boolean gameIsOver = false;
    public List<String> players = new ArrayList<>();
    public List<MESSAGE> chatBuffer = new ArrayList<>();
    public GameInterface Interface;
    public CLI cli;
    static public GUI gui;

    public CONTROLLER(Connection connection , ClientRMI client , interfaceType Interface) throws InterruptedException, IOException {
        if(connection == Connection.RMI){
            if(Interface == interfaceType.GUI){
                gui = new GUI();
                GUI.controller = this;
                GUI.cmd = new CommandsExecutor(this, client, gui);
                this.Interface = new guiHandler(gui);
            }
            if(Interface == interfaceType.CLI ){
                cli  = new CLI(this, client);
                this.Interface = new cliHandler(this.cli);
            }
        }
    }

    public CONTROLLER(Connection connection, ClientTCP client, interfaceType Interface) throws InterruptedException, IOException {
        if(connection == Connection.TCP){
            if(Interface == interfaceType.GUI){
                gui = new GUI();
                GUI.controller = this;
                GUI.cmd = new CommandsExecutor(this, client, gui);
                this.Interface = new guiHandler(gui);
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
    synchronized public boolean getLoginOK(){
        return this.LoginOK;
    }
    synchronized public boolean getReplyPut(){
        return this.reply_put;
    }
    synchronized public boolean getReplyDraw() { return this.reply_draw; }
    synchronized public boolean getGameIsOver() { return this.gameIsOver; }
    synchronized public boolean getGameDataReceived() {
        return this.gameDataReceived;
    }

    /******************************************************************************************************************/

    public void notifyInterface(String message){
        Interface.notifyInterface(message);
    }

    public void setPlayerToPlay( String ptp ) {
        Interface.setPlayerToPlay(this, ptp);
    }

    public void setPlayers(List<String> players){
        Interface.setPlayers(this, players);
    }

    public void setBoard( item[][] grid ){
        Interface.setBoard(this, grid);
    }

    public void setCommonGoals(List<Integer> cardID, List<Integer> token){
        Interface.setCommonGoals(this, cardID, token);
    }

    public void setPersonalGoal(int cardID){
        Interface.setPersonalGoal(this, cardID);
    }

    public void setBookshelf(item[][] bookshelf){
        Interface.setBookshelf(this, bookshelf);
    }

    public void setScore(int score){
        Interface.setScore(this, score);
    }

    public void receiveChat( MESSAGE message){
        Interface.receiveChat(this,message);
    }



    public void setLastRound(){
        Interface.notifyInterface("                                        LAST ROUND                                        ");
    }

    public void startUserInterface(String[] args) {
        Interface.startInterface(args);
    }

    public void restartClient(){
        ClientTCP client = new ClientTCP(Settings.PORT_TCP, false);
        client.controller = this;
        cli.cmd.replaceClient(client);
    }

}

