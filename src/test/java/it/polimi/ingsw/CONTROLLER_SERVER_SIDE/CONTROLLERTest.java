package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.MESSAGE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

//This class tests the methods of the CONTROLLER class
class CONTROLLERTest {

    private CONTROLLER controller;

    @BeforeEach
    void setUpTest() {
        controller = new CONTROLLER();
        controller.setGame(new GAME());
        controller.clientsTCP = new ArrayList<>();
        controller.clientsRMI = new HashMap<>();
    }
    @Test
    void testSetFirstLogin() {
        //It tests the method setFirstLogin() and the LobbySize attribute of the GAME class
        assertFalse(controller.setFirstLogin("Antonio", 5));
        assertTrue(controller.setFirstLogin("Antonio", 3));
        assertEquals(3, controller.game.LobbySize);
    }

    @Test
    void testSetLogin() throws RemoteException {
        //It tests the method setLogin()
        controller.setFirstLogin("Antonio", 3);
        assertFalse(controller.setLogin("Antonio"));
        assertTrue(controller.setLogin("Bruno"));
        assertTrue(controller.setLogin("Chiara"));
        assertTrue(controller.LobbyIsFull);
        assertFalse(controller.setLogin("Davide"));
    }

    @Test
    void testSetDraw() throws RemoteException {
        //It tests the method setDraw()
        controller.setFirstLogin("Antonio", 3);
        controller.setLogin("Bruno");
        controller.setLogin("Chiara");
        controller.game.playerToPlay = "Chiara";
        assertFalse(controller.setDraw("Bruno", 2, 3));
        assertFalse(controller.setDraw("Chiara", 2, 3));
        assertTrue(controller.setDraw("Chiara", 1, 3));
    }

    @Test
    void testSetBookshelf() throws RemoteException {
        //It tests the method setBookshelf()
        controller.setFirstLogin("Antonio", 4);
        controller.setLogin("Beatrice");
        controller.setLogin("Carlo");
        controller.setLogin("Daniela");
        controller.game.playerToPlay = "Carlo";
        assertFalse(controller.setBookshelf("Antonio", 3, 1, 0, -1));
        controller.setDraw("Carlo", 1, 3);
        assertTrue(controller.setBookshelf("Carlo", 3, 0, -1, -1));
    }

    @Test
    void testSetChat() throws RemoteException {
        //It tests the method setChat()
        MESSAGE message = new MESSAGE();
        message.header[0] = "Daniele";
        message.header[1] = "Davide";
        message.text = "Ciao";
        assertEquals(0, controller.game.chat.chat.size());
        controller.setChat(message);
        assertEquals(message, controller.game.chat.chat.get(0));
    }

    @Test
    void testDisconnected() throws RemoteException {
        //It tests the methods disconnected(), OnlyOnePlayerOnline() and the forcedEndTurn() method of the GAME class
        controller.setFirstLogin("Alessia", 4);
        controller.setLogin("Beatrice");
        controller.setLogin("Chiara");
        controller.setLogin("Davide");

        controller.game.playerToPlay = "Alessia";
        controller.disconnected("Beatrice");
        assertTrue(controller.playerList.get(1).disconnected);
        assertFalse(controller.playerList.get(0).disconnected);
        assertFalse(controller.playerList.get(2).disconnected);
        assertFalse(controller.playerList.get(3).disconnected);

        controller.game.playerToPlay = "Chiara";
        controller.disconnected("Chiara");
        assertTrue(controller.game.playerToPlay.equals("Alessia") || controller.game.playerToPlay.equals("Davide"));

        if (controller.game.playerToPlay.equals("Alessia")) {
            controller.disconnected("Alessia");
            assertTrue(controller.playerList.get(0).disconnected);
            assertTrue(controller.game.IsOver);
            assertEquals("Davide", controller.game.space.winner);
        } else {
            controller.disconnected("Davide");
            assertTrue(controller.playerList.get(3).disconnected);
            assertTrue(controller.game.IsOver);
            assertEquals("Alessia", controller.game.space.winner);
        }
    }

    @Test
    void testSetLoginReconnection() throws RemoteException {
        //It tests the methods setLoginReconnection() and newUsername()
        controller.setFirstLogin("Alessio", 3);
        controller.setLogin("Antonio");
        controller.setLogin("Carlo");

        assertFalse(controller.setLoginReconnection("Bruno"));
        assertFalse(controller.setLoginReconnection("Antonio"));

        controller.playerList.get(0).disconnected = true;
        assertTrue(controller.setLoginReconnection("Alessio"));
        assertFalse(controller.playerList.get(0).disconnected);
    }
}