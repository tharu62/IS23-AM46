package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//This class tests the methods of the MASTER class
class MASTERTest {

    @Test
    void testChooseNextPlayer() {
        //It tests the method ChooseFirstPlayerSeat()
        MASTER master = new MASTER();
        PLAYER p1 = new PLAYER(), p2 = new PLAYER();
        p1.username = "Antonio";
        p2.username = "Bruno";
        master.player.add(p1);
        master.player.add(p2);
        master.ChooseFirstPlayerSeat();
        if (master.FirstPlayerSeat.username.equals("Antonio")) assertEquals("Bruno", master.ChooseNextPlayer());
        else assertEquals("Antonio", master.ChooseNextPlayer());
    }

    @Test
    void testCheckIfLastTurn() {
        //It tests the method checkIfLastTurn()
        GAME game = new GAME();
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.master.player.get(0).bookshelf.IsFull = false;
        game.master.player.get(1).bookshelf.IsFull = false;
        game.master.ChooseFirstPlayerSeat();
        if (game.master.FirstPlayerSeat.username.equals("Antonio")) {
            assertFalse(game.master.checkIfLastTurn(game.master.player.get(0).bookshelf));
            game.master.player.get(0).bookshelf.IsFull = true;
            assertTrue(game.master.checkIfLastTurn(game.master.player.get(0).bookshelf));
        } else {
            assertFalse(game.master.checkIfLastTurn(game.master.player.get(1).bookshelf));
        }
    }
}