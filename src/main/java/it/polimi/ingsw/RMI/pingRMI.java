package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.rmi.RemoteException;
import java.util.*;

public class pingRMI extends Thread{
    ClientHandlerRMI clientHandlerRMI;
    CONTROLLER controller;
    Map<GameClient, String> clientsMap;
    List<GameClient> clients = new ArrayList<>();
    int counter = 0;

    public pingRMI(ClientHandlerRMI clientHandlerRMI, CONTROLLER controller, Map<GameClient, String> clientsRMI) {
        this.clientHandlerRMI = clientHandlerRMI;
        this.controller = controller;
        this.clientsMap = clientsRMI;
    }

    @Override
    public void run(){

        while(true) {


            synchronized (this){
                try {
                    wait(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            if(clients != null ) {
                if (counter > 0 && clientHandlerRMI.disconnected) {
                    try {
                        System.out.println(" The player <" + clientsMap.get(clients.get(counter-1)) + "> has disconnected ");
                        controller.disconnected(clientsMap.get(clients.get(counter-1)));
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (counter == clients.size() || counter > clients.size()) {
                    counter = 0;
                } else {
                    try {
                        clientHandlerRMI.disconnected = true;
                        clients.get(counter).ping();

                    } catch (RemoteException e) {
                        //throw new RuntimeException(e);
                    }
                    counter++;
                    updateList();
                }
            }

        }
    }

    synchronized private boolean playerHasNotDisconnected(GameClient gc){
        for(int i = 0; i < controller.playerList.size(); i++){
            if(controller.playerList.get(i).disconnected){
                if(controller.playerList.get(i).username.equals(controller.clientsRMI.get(gc))){
                    return false;
                }
            }
        }
        return true;
    }

    public void updateList(){
        clients.removeAll(clientsMap.keySet());
        clients.addAll(clientsMap.keySet());
    }
}

