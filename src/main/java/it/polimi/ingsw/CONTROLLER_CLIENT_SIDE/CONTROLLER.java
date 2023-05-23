package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.CHAT;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.TCP.Command;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;
import it.polimi.ingsw.VIEW.GUI.GUI;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CONTROLLER{
    public String username;
    public boolean firstToConnect = false;
    public boolean LoginAccepted = false;
    public boolean LobbyIsFull = false;
    public Connection connection;
    public int LobbySize;
    public item[][] grid;
    public boolean myTurn = false;
    public List<String> players = new ArrayList<>();
    public List<COMMON_GOAL_CARD> cards = new ArrayList<>();
    public ClientTCP clientTCP;
    public ClientRMI clientRMI;
    public CLI cli = new CLI(this);
    public GUI gui = new GUI();

    public void notifyCLI(String message){
        cli.notify(message);
    }
    public String getUsername(){
        this.username = cli.getUsername();
        return this.username;
    }

    public int getLobbySize(){
        this.LobbySize = cli.getLobbySize();
        return this.LobbySize;
    }

    public void setPlayerToPlay( String ptp ) throws RemoteException {
        System.out.println(" PTP : " + ptp);
        if( this.username.toLowerCase().equals(ptp) ){

            if(connection == Connection.TCP){
                Command c = new Command();
                c.cmd = CMD.ASK_MY_TURN;
                c.username = this.username;
                String askTurn = clientTCP.g.toJson(c);
                clientTCP.out_ref.println(askTurn);
            }
            if(connection == Connection.RMI){
                System.out.println(" MY TURN : " + myTurn);
                this.myTurn = clientRMI.gs.askMyTurn(this.username);
            }
        }
    }

    public void setBoard( item[][] grid ){
        this.grid = grid;
        cli.printBoard(grid);
    }

    public void setCommonGoals(COMMON_GOAL_CARD card){
        if(cards.size() < 1){
            cards.add(card);
        }else{
            cards.add(card);
            cli.printCommonGoals(this.cards);
        }
    }

    public void setPersonalGoal(PERSONAL_GOAL_CARD card){
        cli.printPersonalGoal(card);
    }

    public void sendChat(String text, String receiver) throws RemoteException {
        if(connection == Connection.TCP){
            Command send = new Command();
            send.cmd = CMD.FROM_CLIENT_CHAT;
            send.chat = new CHAT();
            send.chat.message.text = text;
            send.chat.message.header[0] = this.username;
            send.chat.message.header[1] = receiver;
            clientTCP.CommandSwitcher( send , clientTCP.out_ref);
        }
        if(connection == Connection.RMI){
            MESSAGE m = new MESSAGE();
            m.header[0] = this.username;
            m.header[1] = receiver;
            m.text = text;
            ClientRMI.sendMessage(m);
        }
    }

    synchronized public boolean getMyTurn(){
        return this.myTurn;
    }

}
