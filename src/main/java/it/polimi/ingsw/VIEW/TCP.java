package it.polimi.ingsw.VIEW;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CommunicationProtocol;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.CMD;
import it.polimi.ingsw.TCP.COMANDS.CHAT;
import it.polimi.ingsw.TCP.COMANDS.GAMEPLAY;
import it.polimi.ingsw.TCP.ClientTCP;
import it.polimi.ingsw.TCP.Command;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class TCP implements CommunicationProtocol {
    public ClientTCP clientTCP;
    public TCP(ClientTCP client) {
        this.clientTCP = client;
    }

    @Override
    public void draw(String username, int row, int col, CONTROLLER controller) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.ASK_DRAW;
        send.username = username;
        send.gameplay = new GAMEPLAY();
        send.gameplay.pos = new ArrayList<>();
        send.gameplay.pos.add(0,row);
        send.gameplay.pos.add(1,col);
        clientTCP.CommandSwitcher(send,clientTCP.out_ref);
    }

    @Override
    public void put(String username, int col, int a , int b, int c , CONTROLLER controller) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.ASK_PUT;
        send.username = username;
        send.gameplay = new GAMEPLAY();
        send.gameplay.pos = new ArrayList<>();
        send.gameplay.pos.add(col);
        send.gameplay.pos.add(a);
        send.gameplay.pos.add(b);
        send.gameplay.pos.add(c);
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
    public void endTurn( String username) throws RemoteException {
        Command send = new Command();
        send.cmd = CMD.END_TURN;
        send.username = username;
        clientTCP.CommandSwitcher(send , clientTCP.out_ref);
    }

    @Override
    public void replaceClient(ClientTCP clientTCP) {
        this.clientTCP = clientTCP;
    }

    @Override
    public void replaceClient(ClientRMI clientRMI) {
        //
    }

    @Override
    public void startClientRMI() {
        // USED ONLY FOR IN GUI
    }

}
