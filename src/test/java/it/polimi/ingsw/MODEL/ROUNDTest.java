package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ROUNDTest {
    @Test
    void testUpdate() {
        //It tests the method update(), the roundTrip and last attributes and the TURN class attribute count
        GAME game = new GAME();
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.master.ChooseFirstPlayerSeat();
        assertEquals(2, game.master.round.roundTrip);
        game.master.round.update(game.master.player.get(0).bookshelf);
        assertEquals(1, game.master.round.turn.count);
        game.master.round.update(game.master.player.get(1).bookshelf);
        assertEquals(2, game.master.round.turn.count);
        game.master.player.get(0).bookshelf.IsFull = true;
        game.master.round.update(game.space.player.get(0).bookshelf);
        assertTrue(game.master.round.last);
        assertEquals(0, game.master.round.turn.count);
    }
}