package it.polimi.ingsw.TCP;

import it.polimi.ingsw.CONTROLLER_SERVER_SIDE.CONTROLLER;
import it.polimi.ingsw.RMI.GameClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerTCP {
    CONTROLLER controller;
    final int port;
    public List<ClientHandler> clients= new ArrayList<>();

    public ServerTCP(CONTROLLER Controller, int port){
        this.controller= Controller;
        this.port= port;
    }

    public void start( List<GameClient> clientsRMI ){
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return;
        }
        System.out.println("Server ready");

        // main loop TCP
        while (!controller.GameIsOver) {
            try {
                Socket socket = serverSocket.accept();
                executor.submit(new ClientHandler(socket, controller, clients, clientsRMI));

                /** PHASE 1
                 *   After the login phase the Server sends the Board, the Common_goal_cards and player_to_play.
                 */
                 if (controller.game.master.round.turn.count == 0 && controller.LobbyIsFull && !controller.GameHasStarted) {
                     Command temp;
                     for (GameClient gc : clientsRMI) {
                         gc.receiveBoard(controller.getBoard());
                         gc.receiveCommonGoals(controller.getCommonGoalCard());
                         gc.receivePlayerToPlay(controller.game.playerToPlay);
                     }

                     temp = new Command();
                     temp.cmd = "BOARD";
                     temp.broadcast.grid = controller.getBoard();
                     clients.get(0).broadcast(temp);

                     temp = new Command();
                     temp.cmd = "COMMON_GOALS";
                     temp.broadcast.cards = controller.getCommonGoalCard();
                     clients.get(0).broadcast(temp);

                     temp = new Command();
                     temp.cmd = "PLAYER_TO_PLAY";
                     temp.broadcast.ptp = controller.game.playerToPlay;
                     clients.get(0).broadcast(temp);

                     controller.GameHasStarted = true;
                 }

                 /**
                  * PHASE 2
                  */
                 if (controller.TurnHasStarted) {
                     Timer timer = new Timer();
                     //TODO
                     // TimerTask task = new TimerTask() { };
                     // long delay = 0;
                     // long period = 1000L;
                     // timer.schedule(task, delay, period);
                     // if(draw){
                     // task.cancel();
                     // task = new TimerTask();
                     // }...
                 }

                 /** PHASE 3
                  * If it's the last round and the last turn, the game is over, the model autonomously calculate the scores
                  * and finds the winner.
                  */
                 if (controller.game.master.round.last && controller.game.master.round.turn.count == (controller.game.playerNumber - 1)) {
                     controller.GameIsOver = true;
                     //TODO
                     // temp = new Command();
                     // temp.cmd = "WINNER";
                     // temp.broadcast.ptp = controller.game.space.winner
                     // clientsTCP.get(0).broadcast(temp);
                 }

            } catch(IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

}
