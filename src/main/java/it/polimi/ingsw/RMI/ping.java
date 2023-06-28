package it.polimi.ingsw.RMI;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;

import java.rmi.RemoteException;
import java.util.*;

public class ping extends Thread{
    CONTROLLER controller;
    Map<GameClient, String> clientsRMI;
    List<GameClient> clients = new ArrayList<>();

    public ping(CONTROLLER controller, Map<GameClient, String> clientsRMI) {
        this.controller = controller;
        this.clientsRMI = clientsRMI;
    }

    @Override
    public void run(){
        while(true){

            synchronized (this){
                try {
                    wait(4000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            synchronized (controller.lock) {
                if ( clientsRMI != null && clientsRMI.size() > 0 && clients.size() == clientsRMI.size()) {
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        int clientSize = clientsRMI.size()-1;
                        boolean pingValid = false;
                        @Override
                        public void run() {
                            try {

                                GameClient gc = clients.get(clientSize);
                                if(playerHasNotDisconnected(gc)){
                                    pingValid = gc.ping();
                                }
                                if( clientSize == 0){
                                    timer.cancel();
                                }

                            } catch (RemoteException e) {
                                try {
                                    System.out.println(" THE player < " + clientsRMI.get(clients.get(clientSize)) + " > has disconnected ");
                                    if (clientsRMI.get(clients.get(clientSize)) == null) {
                                        clientsRMI.remove(clients.get(clientSize));
                                    } else {
                                        controller.disconnected(clientsRMI.get(clients.get(clientSize)));
                                    }
                                } catch (RemoteException ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                            if( clientSize == 0){
                                timer.cancel();
                            }
                            clientSize--;
                        }
                    }; timer.scheduleAtFixedRate(timerTask, 0, 1000);
                }

                updateList();
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
        clients.removeAll(clientsRMI.keySet());
        clients.addAll(clientsRMI.keySet());
    }
}

