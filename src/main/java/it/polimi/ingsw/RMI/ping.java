package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class ping extends Thread{
    CONTROLLER controller;
    public Map<GameClient, String> clientsRMIUsername;

    public ping(CONTROLLER controller, Map<GameClient, String> clientsRMIUsername) {
        this.controller = controller;
        this.clientsRMIUsername = clientsRMIUsername;
    }

    public void run(){
        while(true){
            try {
                synchronized (this){
                    wait(5000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for (GameClient gc : clientsRMIUsername.keySet()) {
                try {
                    if(!playerHasDisconnected(gc)){
                        gc.ping();
                    }
                } catch (RemoteException e) {
                    try {
                        controller.disconnected(clientsRMIUsername.get(gc));
                    } catch (RemoteException ex) {
                        //throw new RuntimeException(ex);
                    }
                }

            }
        }
    }

    private boolean playerHasDisconnected(GameClient gc){
        for(int i = 0; i < controller.playerList.size(); i++){
            if(controller.playerList.get(i).disconnected){
                if(controller.playerList.get(i).username.equals(controller.clientRmiUsername.get(gc))){
                    return true;
                }
            }
        }
        return false;
    }
}

