package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PERSONAL_GOAL_CARDTest {

    @Test
    void testCheckCardLogic() {
        PERSONAL_GOAL_CARD personalGoalCard = new PERSONAL_GOAL_CARD();
        personalGoalCard.cardLogic = new P_CARD_LOGIC_10();
        BOOKSHELF bookshelf = new BOOKSHELF();
        assertEquals(0, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[5][3] = item.PLANTS;
        assertEquals(1, personalGoalCard.checkCardLogic(bookshelf));
        bookshelf.Grid[3][3] = item.CATS;
        assertEquals(2, personalGoalCard.checkCardLogic(bookshelf));
    }
}