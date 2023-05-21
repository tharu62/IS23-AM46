package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.CHAT;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.TCP.Command;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;

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
    public String playerToPlay;
    public ClientTCP clientTCP;
    public ClientRMI clientRMI;
    public CLI cli = new CLI(this);

    public void notifyCLI(String message){
        cli.notify(message);
    }
    public String getUsername(){
        return cli.getUsername();
    }

    public int getLobbySize(){
        this.LobbySize = cli.getLobbySize();
        return this.LobbySize;
    }

    public void setPlayerToPlay( String ptp){
        this.playerToPlay = ptp;
        //TODO
    }

    public void setBoard( item[][] grid ){
        this.grid = grid;
        cli.printBoard(grid);
        //TODO
    }

    public void setCommonGoals(List<COMMON_GOAL_CARD> list){
        cli.printCommonGoals(list);
        //TODO
    }

    public void setPersonalGoal(PERSONAL_GOAL_CARD card){
        cli.printPersonalGoal(card);
        //TODO
    }

    public void sendChat( String text){
        if(connection == Connection.TCP){
            Command send = new Command();
            send.cmd = CMD.FROM_CLIENT_CHAT;
            send.chat = new CHAT();
            send.chat.message.text = text;
            send.chat.message.header[0] = this.username;
            //send.chat.message.header[1] = System.nanoTime();
            clientTCP.CommandSwitcher( send , clientTCP.out_ref);
        }
        if(connection == Connection.RMI){
            //TODO
        }
    }
    
}
