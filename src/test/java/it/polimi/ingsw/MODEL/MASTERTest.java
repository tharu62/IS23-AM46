package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MASTERTest {

    @Test
    void testChooseNextPlayer() {
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