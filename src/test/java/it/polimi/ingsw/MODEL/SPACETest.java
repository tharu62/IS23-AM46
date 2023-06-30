package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//It tests the methods of the SPACE class
class SPACETest {

    @Test
    void testCalculateScore() {
        //It tests the method calculateScore()
        SPACE space = new SPACE();
        PLAYER p1 = new PLAYER(), p2 = new PLAYER(), p3 = new PLAYER();
        p1.username = "Antonio";
        p2.username = "Bruno";
        p3.username = "Chiara";
        p1.personal.cardLogic = new P_CARD_LOGIC_9();
        p2.personal.cardLogic = new P_CARD_LOGIC_3();
        p3.personal.cardLogic = new P_CARD_LOGIC_6();
        List<PLAYER> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);
        for (int i = 0; i < 3; i++) {
            players.get(i).bookshelf.Grid[5][0] = item.FRAMES;
            players.get(i).bookshelf.Grid[5][1] = item.FRAMES;
            players.get(i).bookshelf.Grid[5][2] = item.FRAMES;
            players.get(i).bookshelf.Grid[5][3] = item.FRAMES;
            players.get(i).bookshelf.Grid[5][4] = item.PLANTS;
            players.get(i).bookshelf.Grid[4][4] = item.PLANTS;
        }
        space.player.addAll(players);
        assertEquals("Antonio", space.calculateScore());
        assertEquals(5, space.player.get(0).score);
        assertEquals(3, space.player.get(1).score);
        assertEquals(3, space.player.get(2).score);
    }

    @Test
    void testPlaceItem() {
        //It tests the method placeItem()
        GAME game= new GAME();
        game.addPlayer("Giovanni");
        game.addPlayer("Antonio");
        game.setBoard();
        game.space.player.get(0).bookshelf.itemToPut.add(item.FRAMES);
        game.space.player.get(0).bookshelf.itemToPut.add(item.FRAMES);
        game.space.player.get(0).bookshelf.itemToPut.add(item.TROPHIES);
        assertTrue(game.space.placeItem(0, 1, 0, 1, 2));

        game.space.player.get(0).bookshelf.itemToPut.add(item.PLANTS);
        game.space.player.get(0).bookshelf.itemToPut.add(item.CATS);
        assertTrue(game.space.placeItem(0, 1, 1, 2, -1));

        game.space.player.get(0).bookshelf.itemToPut.add(item.CATS);
        game.space.player.get(0).bookshelf.itemToPut.add(item.GAMES);
        assertFalse(game.space.placeItem(0, 1, 2, 1, -1));
    }

    @Test
    void testDraw() {
        //It tests the method draw()
        GAME game= new GAME();
        game.addPlayer("Giovanni");
        game.addPlayer("Antonio");
        game.setBoard();
        assertTrue(game.space.draw(0, 1, 3));
        assertTrue(game.space.draw(0, 1, 4));
        assertFalse(game.space.draw(0, 2, 3));

        game.space.drawCounter = 0;
        game.space.board.itemCounter = 0;
        assertFalse(game.space.draw(1, 1, 3));
        assertFalse(game.space.draw(1, 3, 3));
        assertTrue(game.space.draw(1, 2, 3));
        assertTrue(game.space.draw(1, 2, 4));
        assertTrue(game.space.draw(1, 2, 5));

        game.space.drawCounter = 0;
        game.space.board.itemCounter = 0;
        assertTrue(game.space.draw(0, 3, 3));
        assertTrue(game.space.draw(0, 3, 4));
        assertTrue(game.space.draw(0, 3, 5));
        assertFalse(game.space.draw(0, 3, 6));

        game.space.drawCounter = 0;
        game.space.board.itemCounter = 0;
        assertTrue(game.space.draw(1, 4, 1));
        assertTrue(game.space.draw(1, 5, 1));
        assertFalse(game.space.draw(1, 6, 1));
        assertFalse(game.space.draw(1, 4, 2));

        game.space.drawCounter = 0;
        game.space.board.itemCounter = 0;
        assertTrue(game.space.draw(0, 3, 2));
        assertTrue(game.space.draw(0, 4, 2));
        assertTrue(game.space.draw(0, 5, 2));
    }
}