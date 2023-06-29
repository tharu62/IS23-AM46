package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PERSONALGOALCARDTest {

    private BOOKSHELF bookshelf;
    private PERSONAL_GOAL_CARD personalGoalCard;
    @Test
    void testCheckCardLogic1() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_1();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][0] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][4] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][3] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][3] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][1] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][2] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic2() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_2();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][1] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][0] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][2] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][2] = item.GAMES;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][4] = item.BOOKS;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][4] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][3] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic3() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_3();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][2] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][1] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][3] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][0] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][4] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic4() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_4();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][3] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][2] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][1] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][1] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][4] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][2] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][0] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic5() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_5();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][4] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][3] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][2] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][2] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][1] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][1] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic6() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_6();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][4] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][3] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][3] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][1] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][3] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic7() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_7();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][1] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][0] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][2] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][2] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][4] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][3] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][0] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic8() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_8();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][0] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][1] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][3] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][3] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][3] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][4] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][2] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic9() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_9();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][4] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][2] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][4] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][4] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][1] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic10() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_10();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][3] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][3] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][0] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][0] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][1] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][1] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][4] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic11() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_11();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][4] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][1] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][1] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][0] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][2] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][3] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

    @Test
    void testCheckCardLogic12() {
        personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_12();
        bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[1][1] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][0] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.TROPHIES;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.BOOKS;
        assertEquals(4, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[4][4] = item.GAMES;
        assertEquals(6, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[2][2] = item.FRAMES;
        assertEquals(9, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][3] = item.TROPHIES;
        assertEquals(12, personalGoalCard.checkCardLogic(bookshelf));
    }

}