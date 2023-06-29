package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DRAWTest {

    private DRAW draw;
    private TOKEN_GENERATOR tokenGenerator = new TOKEN_GENERATOR();
    private BOOKSHELF bookshelf;
    @BeforeEach
    void setUp(){
        draw = new DRAW();
        COMMON_GOAL_CARD c1 = new COMMON_GOAL_CARD();
        c1.cardLogic = new CARD_LOGIC_9();
        COMMON_GOAL_CARD c2 = new COMMON_GOAL_CARD();
        c2.cardLogic = new CARD_LOGIC_10();
        draw.card.add(c1);
        draw.card.add(c2);
        bookshelf = new BOOKSHELF();
        int cont = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (cont == 8) break;
                bookshelf.Grid[i][j] = item.TROPHIES;
                cont++;
            }
        }
    }
    @Test
    void testCheck3players() {
        tokenGenerator.player_number = 3;
        draw.card.get(0).SetToken(tokenGenerator.setTokenLogic());
        draw.card.get(1).SetToken(tokenGenerator.setTokenLogic());
        assertEquals(8, draw.check(bookshelf, false, true));
        assertEquals(6, draw.check(bookshelf, false, true));
        assertEquals(4, draw.check(bookshelf, false, true));
    }

    @Test
    void testCheck4players(){
        tokenGenerator.player_number = 4;
        draw.card.get(0).SetToken(tokenGenerator.setTokenLogic());
        draw.card.get(1).SetToken(tokenGenerator.setTokenLogic());
        assertEquals(8, draw.check(bookshelf, false, true));
        assertEquals(6, draw.check(bookshelf, false, true));
        assertEquals(4, draw.check(bookshelf, false, true));
        assertEquals(2, draw.check(bookshelf, false, true));
    }
}