package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SPACETest {

    @Test
    void testCalculateScore() {
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
}