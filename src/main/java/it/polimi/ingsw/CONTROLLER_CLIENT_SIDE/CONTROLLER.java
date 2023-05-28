package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.CHAT;
import it.polimi.ingsw.TCP.COMANDS.GAMEPLAY;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.TCP.Command;
import it.polimi.ingsw.VIEW.CLI.CLI;
import it.polimi.ingsw.MODEL.*;
//import it.polimi.ingsw.VIEW.GUI.GUI;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class CONTROLLER{
    public Connection connection;
    public String username;
    public boolean firstToConnect = false;
    public boolean LoginOK = false;
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
    public ClientTCP clientTCP;
    public ClientRMI clientRMI;
    public CLI cli = new CLI(this);

    public boolean draw_end = false;
    public boolean end_turn = false;
    public boolean put_end = false;
    //public GUI gui = new GUI();

    /******************************************************************************************************************/
    public void notifyCLI(String message){
        cli.notify(message);
    }
    synchronized public String getUsername(){
        this.username = cli.getUsername();
        return this.username;
    }

    public int getPersonalGoal() throws RemoteException {
        if(PersonalGoalCardID != -1 ){
            return PersonalGoalCardID;
        }
        if(connection == Connection.TCP){
            Command c = new Command();
            c.cmd = CMD.SEND_PERSONAL_GOAL_CARD;
            c.username = this.username;
            String askPersonal = clientTCP.g.toJson(c);
            clientTCP.out_ref.println(askPersonal);
            return cli.replyPersonal();
        }
        if(connection == Connection.RMI){
            PersonalGoalCardID = ClientRMI.gs.sendPersonalGoal(username);
            return PersonalGoalCardID;
        }
        return -1;
    }

    synchronized public int getLobbySize(){
        this.LobbySize = cli.getLobbySize();
        return this.LobbySize;
    }

    synchronized public boolean getMyTurn(){
        return myTurn;
    }

    /******************************************************************************************************************/
    synchronized public void setPlayerToPlay( String ptp ) throws RemoteException {
        if( this.username.toLowerCase().equals(ptp) ){
            if(connection == Connection.TCP){
                Command c = new Command();
                c.cmd = CMD.ASK_MY_TURN;
                c.username = this.username;
                String askTurn = clientTCP.g.toJson(c);
                clientTCP.out_ref.println(askTurn);
            }
            if(connection == Connection.RMI){
                myTurn = ClientRMI.gs.askMyTurn( this.username );
            }
        }
    }

    public void setBoard( item[][] grid ){
        this.grid = grid;
        cli.printBoard(grid);
    }

    public void setCommonGoals(int  cardID){
        boolean setNotDone = true;
        if(cards.size() == 0){
            cards.add(cardID);
            setNotDone = false;
        }
        if(cards.size() == 1 && setNotDone){
            cards.add(cardID);
            cli.printCommonGoals(cards);
            GameHasStarted = true;
            setNotDone = false;
        }
        if(cards.size() == 2 && setNotDone){
            cards = new ArrayList<>();
            cards.add(cardID);
        }
    }

    public void setPersonalGoal(int cardID){
        cli.printPersonalGoal(cardID);
    }

    synchronized public void sendChat(String text, String receiver) throws RemoteException {
        if(connection == Connection.TCP){
            Command send = new Command();
            send.cmd = CMD.FROM_CLIENT_CHAT;
            send.chat = new CHAT();
            send.chat.message = new MESSAGE();
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

    public void receiveChat( MESSAGE message){
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

    public boolean setDraw( int row, int col) throws RemoteException {
        if(connection == Connection.TCP){
            Command send = new Command();
            send.cmd = CMD.ASK_DRAW;
            send.gameplay = new GAMEPLAY();
            send.gameplay.pos = new ArrayList<>();
            send.gameplay.pos.add(row);
            send.gameplay.pos.add(col);
            clientTCP.CommandSwitcher(send,clientTCP.out_ref);
            return cli.reply();
        }
        if(connection == Connection.RMI) {
            return ClientRMI.gs.askDraw(this.username, row, col);
        }
        return false;
    }

    public boolean setPut(int a, int b, int c ,int col) throws RemoteException {
        if(connection == Connection.TCP){
            Command send = new Command();
            send.cmd = CMD.ASK_PUT_ITEM;
            send.username = this.username;
            send.gameplay = new GAMEPLAY();
            send.gameplay.pos = new ArrayList<>();
            send.gameplay.pos.add(col);
            send.gameplay.pos.add(a);
            send.gameplay.pos.add(b);
            send.gameplay.pos.add(c);
            clientTCP.CommandSwitcher( send , clientTCP.out_ref);
            put[0] = col;
            put[1] = a;
            put[2] = b;
            put[3] = c;
            return cli.reply();
        }
        if(connection == Connection.RMI){
            put_valid = ClientRMI.gs.askPutItem(this.username,col,a,b,c);
            if(put_valid){
                put[0] = col;
                put[1] = a;
                put[2] = b;
                put[3] = c;
            }
            return put_valid;
        }
        return false;
    }

    public void setBookshelf() throws RemoteException {
        //RMI
        cli.printBookshelf(ClientRMI.gs.sendBookshelf(username));
        //TCP
    }

    public void endTurn() throws RemoteException {
        draw_end = false;
        put_end = false;
        end_turn = false;
        if(connection == Connection.TCP){
            myTurn = false;
            Command send = new Command();
            send.cmd = CMD.END_TURN;
            send.username = this.username;
            clientTCP.CommandSwitcher( send , clientTCP.out_ref);
        }
        if(connection == Connection.RMI){
            myTurn = false;
            ClientRMI.gs.endTurn(this.username);
        }
    }
    public void setLastRound(){
        cli.notify("******************************************************************************************");
        cli.notify(" LAST ROUND!!! ");
        cli.notify("******************************************************************************************");
    }

}
