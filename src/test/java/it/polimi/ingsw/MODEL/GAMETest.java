package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;

public class GAMETest extends TestCase {

    public void testAddPlayer() {
    }

    public void testSetBoard() {
    }

    public void testDrawCommonGoalCards() {
    }

    public void testDrawPersonalGoalCards() {
    }

    public void testChooseFirstPlayerSeat() {
    }

    public void testMasterStartTurn() {
    }

    public void testPlayerDrawItem() {
        GAME game= new GAME();
        assertTrue(game.playerDrawItem("giovanni", 2, 2));
    }

    public void testPlayerPutItems() {
    }

    public void testPlayerWantsToCheckScore() {
    }
}