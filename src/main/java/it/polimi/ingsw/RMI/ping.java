package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class ping extends Thread{
    CONTROLLER controller;
    public List<GameClient> clientsRMI = new ArrayList<>();

    public ping(CONTROLLER controller, List<GameClient> clientsRMI) {
        this.controller = controller;
        this.clientsRMI = clientsRMI;
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
            for (GameClient gc : clientsRMI) {
                try {
                    gc.ping();
                } catch (RemoteException e) {
                    try {
                        controller.disconnected("someone");
                    } catch (RemoteException ex) {
                        //throw new RuntimeException(ex);
                    }
                }

            }

        }
    }
}

