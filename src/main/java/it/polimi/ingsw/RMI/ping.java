package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.rmi.RemoteException;
import java.util.Map;

public class ping extends Thread{
    CONTROLLER controller;
    public Map<GameClient, String> clientsRMI;

    public ping(CONTROLLER controller, Map<GameClient, String> clientsRMI) {
        this.controller = controller;
        this.clientsRMI = clientsRMI;
    }

    @Override
    public void run(){
        while(true){

            /*
            try {
                synchronized (this){
                    wait(500);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

             */

            synchronized (controller.lock) {
                if (clientsRMI.size() > 0) {
                    for (GameClient gc : clientsRMI.keySet()) {
                        try {

                            if (playerHasNotDisconnected(gc)) {
                                gc.ping();
                            }

                        } catch (RemoteException e) {
                            try {

                                controller.disconnected(clientsRMI.get(gc));

                            } catch (RemoteException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
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

}

