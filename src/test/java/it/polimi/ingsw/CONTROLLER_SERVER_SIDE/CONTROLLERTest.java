package it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

import it.polimi.ingsw.MODEL.GAME;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CONTROLLERTest {

    private CONTROLLER controller;

    @BeforeEach
    void setUpTest() {
        controller = new CONTROLLER();
        controller.setGame(new GAME());
        controller.clientsTCP = new ArrayList<>();
        controller.clientsRMI = new ArrayList<>();
    }
    @Test
    void setFirstLogin() {
        assertFalse(controller.setFirstLogin("Antonio", 5));
        assertTrue(controller.setFirstLogin("Antonio", 3));
        assertEquals(3, controller.game.LobbySize);
    }

    @Test
    void setLogin() throws RemoteException {
        controller.setFirstLogin("Antonio", 3);
        assertFalse(controller.setLogin("Antonio"));
        assertTrue(controller.setLogin("Bruno"));
        assertTrue(controller.setLogin("Chiara"));
        assertTrue(controller.LobbyIsFull);
        assertFalse(controller.setLogin("Davide"));
    }

    @Test
    void setTurn() {

    }

    @Test
    void setDraw() throws RemoteException {
        controller.setFirstLogin("Antonio", 3);
        controller.setLogin("Bruno");
        controller.setLogin("Chiara");
        controller.game.playerToPlay = "Chiara";
        assertFalse(controller.setDraw("Bruno", 2, 3));
        assertFalse(controller.setDraw("Chiara", 2, 3));
        assertTrue(controller.setDraw("Chiara", 1, 3));
    }

    @Test
    void setBookshelf() {

    }

    @Test
    void setScore() {
    }

    @Test
    void setEndTurn() {
    }

    @Test
    void setChat() {
    }

    @Test
    void disconnected() {
    }
}