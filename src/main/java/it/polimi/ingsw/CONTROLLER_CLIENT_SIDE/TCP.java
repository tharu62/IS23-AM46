package it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;

import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.CHAT;
import it.polimi.ingsw.TCP.COMANDS.GAMEPLAY;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.TCP.Command;
import it.polimi.ingsw.VIEW.CLI.CLI;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class TCP implements COM{
    public ClientTCP clientTCP;
    public TCP(ClientTCP client) {
        this.clientTCP = client;
    }

    @Override
    public int getPersonalGoal(int PersonalGoalCardID, String username, CLI cli) {
        Command c = new Command();
        c.cmd = CMD.SEND_PERSONAL_GOAL_CARD;
        c.username = username;
        String askPersonal = clientTCP.g.toJson(c);
        clientTCP.out_ref.println(askPersonal);
        return cli.cmd.replyPersonal();
    }

    @Override
    public boolean draw(String username, int row, int col, boolean draw_valid, boolean reply_draw) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.ASK_DRAW;
        send.username = username;
        send.gameplay = new GAMEPLAY();
        send.gameplay.pos = new ArrayList<Integer>();
        send.gameplay.pos.add(0,row);
        send.gameplay.pos.add(1,col);
        clientTCP.CommandSwitcher(send,clientTCP.out_ref);
        return true;
    }

    @Override
    public boolean put(String username, int col, int a , int b, int c , boolean put_valid,  boolean reply_put) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.ASK_PUT_ITEM;
        send.username = username;
        send.gameplay = new GAMEPLAY();
        send.gameplay.pos = new ArrayList<>();
        send.gameplay.pos.add(col);
        send.gameplay.pos.add(a);
        send.gameplay.pos.add(b);
        send.gameplay.pos.add(c);
        clientTCP.CommandSwitcher(send,clientTCP.out_ref);
        return true;
    }

    @Override
    public void bookshelf(CLI cli , String username) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.ASK_BOOKSHELF;
        send.username = username;
        clientTCP.CommandSwitcher(send,clientTCP.out_ref);
    }

    @Override
    public void sendChat(String username ,String text, String receiver) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.FROM_CLIENT_CHAT;
        send.chat = new CHAT();
        send.chat.message = new MESSAGE();
        send.chat.message.text = text;
        send.chat.message.header[0] = username;
        send.chat.message.header[1] = receiver;
        clientTCP.CommandSwitcher( send , clientTCP.out_ref);
    }

    @Override
    public void endTurn(boolean myTurn, String username) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.END_TURN;
        send.username = username;
        clientTCP.CommandSwitcher(send , clientTCP.out_ref);
    }

    //TODO passare come argomento oggetto controller e non gli attributi, altrimenti non c'è passaggio di reference ma solo di value.
}
