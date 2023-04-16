package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CARD_LOGICTest {

    @Test
    void testCheckCardLogic1() {
        CARD_LOGIC card = new CARD_LOGIC_1();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.CATS;
        bookshelf.Grid[5][1] = item.CATS;
        bookshelf.Grid[5][2] = item.CATS;
        bookshelf.Grid[5][3] = item.CATS;
        bookshelf.Grid[5][4] = item.CATS;
        bookshelf.Grid[4][0] = item.CATS;
        bookshelf.Grid[4][1] = item.CATS;
        bookshelf.Grid[4][2] = item.CATS;
        assertTrue(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[3][0] = item.CATS;
        assertFalse(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic2() {
        CARD_LOGIC card = new CARD_LOGIC_2();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.PLANTS;
        bookshelf.Grid[5][2] = item.PLANTS;
        bookshelf.Grid[3][0] = item.PLANTS;
        bookshelf.Grid[3][2] = item.PLANTS;
        bookshelf.Grid[4][1] = item.PLANTS;
        assertTrue(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[4][0] = item.PLANTS;
        assertTrue(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic3() {
        CARD_LOGIC card = new CARD_LOGIC_3();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[0][0] = item.PLANTS;
        bookshelf.Grid[5][0] = item.PLANTS;
        bookshelf.Grid[5][4] = item.PLANTS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[0][4] = item.PLANTS;
        assertTrue(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic4() {
        CARD_LOGIC card = new CARD_LOGIC_4();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        for (int i = 0; i < 4; i++) bookshelf.Grid[i + 1][i] = item.BOOKS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][4] = item.BOOKS;
        assertTrue(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic5() {
        CARD_LOGIC card = new CARD_LOGIC_5();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.BOOKS;
        bookshelf.Grid[4][0] = item.CATS;
        bookshelf.Grid[3][0] = item.FRAMES;
        bookshelf.Grid[2][0] = item.TROPHIES;
        bookshelf.Grid[1][0] = item.PLANTS;
        bookshelf.Grid[0][0] = item.GAMES;
        bookshelf.Grid[5][1] = item.BOOKS;
        bookshelf.Grid[4][1] = item.CATS;
        bookshelf.Grid[3][1] = item.FRAMES;
        bookshelf.Grid[2][1] = item.TROPHIES;
        bookshelf.Grid[1][1] = item.PLANTS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[0][1] = item.GAMES;
        assertTrue(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic6() {
        CARD_LOGIC card = new CARD_LOGIC_6();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.BOOKS;
        bookshelf.Grid[4][0] = item.CATS;
        bookshelf.Grid[3][0] = item.CATS;
        bookshelf.Grid[2][0] = item.TROPHIES;
        bookshelf.Grid[1][0] = item.TROPHIES;
        bookshelf.Grid[0][0] = item.CATS;
        bookshelf.Grid[5][1] = item.GAMES;
        bookshelf.Grid[4][1] = item.GAMES;
        bookshelf.Grid[3][1] = item.GAMES;
        bookshelf.Grid[2][1] = item.GAMES;
        bookshelf.Grid[1][1] = item.PLANTS;
        bookshelf.Grid[0][1] = item.PLANTS;
        bookshelf.Grid[5][2] = item.GAMES;
        bookshelf.Grid[4][2] = item.GAMES;
        bookshelf.Grid[3][2] = item.GAMES;
        bookshelf.Grid[2][2] = item.BOOKS;
        bookshelf.Grid[1][2] = item.PLANTS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.CATS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.PLANTS;
        assertTrue(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic7() {
        CARD_LOGIC card = new CARD_LOGIC_7();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.PLANTS;
        bookshelf.Grid[5][1] = item.GAMES;
        bookshelf.Grid[5][2] = item.FRAMES;
        bookshelf.Grid[5][3] = item.CATS;
        bookshelf.Grid[5][4] = item.BOOKS;
        bookshelf.Grid[4][0] = item.PLANTS;
        bookshelf.Grid[4][1] = item.GAMES;
        bookshelf.Grid[4][2] = item.BOOKS;
        bookshelf.Grid[4][3] = item.CATS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[4][4] = item.PLANTS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[4][4] = item.TROPHIES;
        assertTrue(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic8() {
        CARD_LOGIC card = new CARD_LOGIC_8();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.PLANTS;
        bookshelf.Grid[5][1] = item.GAMES;
        bookshelf.Grid[5][2] = item.PLANTS;
        bookshelf.Grid[5][3] = item.PLANTS;
        bookshelf.Grid[5][4] = item.BOOKS;

        bookshelf.Grid[4][0] = item.PLANTS;
        bookshelf.Grid[4][1] = item.PLANTS;
        bookshelf.Grid[4][2] = item.PLANTS;
        bookshelf.Grid[4][3] = item.PLANTS;
        bookshelf.Grid[4][4] = item.PLANTS;

        bookshelf.Grid[3][0] = item.PLANTS;
        bookshelf.Grid[3][1] = item.GAMES;
        bookshelf.Grid[3][2] = item.GAMES;
        bookshelf.Grid[3][3] = item.CATS;
        bookshelf.Grid[3][4] = item.CATS;

        bookshelf.Grid[2][0] = item.PLANTS;
        bookshelf.Grid[2][1] = item.GAMES;
        bookshelf.Grid[2][2] = item.GAMES;
        bookshelf.Grid[2][3] = item.CATS;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[2][4] = item.TROPHIES;
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[2][4] = item.GAMES;
        assertTrue(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic9() {
        CARD_LOGIC card = new CARD_LOGIC_9();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.PLANTS;
        bookshelf.Grid[5][1] = item.PLANTS;
        bookshelf.Grid[4][1] = item.PLANTS;
        bookshelf.Grid[4][0] = item.PLANTS;

        bookshelf.Grid[5][2] = item.BOOKS;
        bookshelf.Grid[5][3] = item.BOOKS;
        bookshelf.Grid[4][2] = item.BOOKS;
        bookshelf.Grid[4][3] = item.BOOKS;
        assertTrue(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][4] = item.BOOKS;
        assertFalse(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic10() {
        CARD_LOGIC card = new CARD_LOGIC_10();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        for (int i = 0; i < 5; i++) {
            for (int j = 5; j >= i; j--) {
                bookshelf.Grid[j][i] = item.BOOKS;
            }
        }
        assertTrue(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[3][4] = item.CATS;
        assertFalse(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic11() {
        CARD_LOGIC card = new CARD_LOGIC_11();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.BOOKS;
        bookshelf.Grid[4][0] = item.BOOKS;

        bookshelf.Grid[5][1] = item.TROPHIES;
        bookshelf.Grid[4][1] = item.TROPHIES;
        bookshelf.Grid[3][1] = item.TROPHIES;

        bookshelf.Grid[5][2] = item.CATS;
        bookshelf.Grid[5][3] = item.CATS;
        bookshelf.Grid[5][4] = item.CATS;

        bookshelf.Grid[4][3] = item.TROPHIES;
        bookshelf.Grid[4][4] = item.TROPHIES;

        bookshelf.Grid[4][2] = item.FRAMES;
        bookshelf.Grid[3][2] = item.FRAMES;
        bookshelf.Grid[3][3] = item.FRAMES;

        bookshelf.Grid[3][0] = item.PLANTS;
        bookshelf.Grid[2][0] = item.PLANTS;

        assertTrue(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[4][2] = item.TROPHIES;
        assertFalse(card.CheckCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic12() {
        CARD_LOGIC card = new CARD_LOGIC_12();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertFalse(card.CheckCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.BOOKS;
        bookshelf.Grid[4][0] = item.BOOKS;
        bookshelf.Grid[5][1] = item.BOOKS;
        bookshelf.Grid[4][1] = item.BOOKS;
        bookshelf.Grid[4][2] = item.BOOKS;

        bookshelf.Grid[5][2] = item.CATS;
        bookshelf.Grid[5][3] = item.CATS;
        bookshelf.Grid[5][4] = item.CATS;
        bookshelf.Grid[4][4] = item.CATS;
        bookshelf.Grid[3][4] = item.CATS;

        bookshelf.Grid[4][3] = item.FRAMES;
        bookshelf.Grid[3][3] = item.FRAMES;
        bookshelf.Grid[1][3] = item.FRAMES;
        bookshelf.Grid[2][3] = item.FRAMES;

        bookshelf.Grid[3][2] = item.CATS;
        bookshelf.Grid[3][1] = item.CATS;
        bookshelf.Grid[3][0] = item.CATS;
        bookshelf.Grid[2][1] = item.CATS;
        assertTrue(card.CheckCardLogic(bookshelf));

        bookshelf.Grid[4][2] = item.CATS;
        assertFalse(card.CheckCardLogic(bookshelf));
    }
}