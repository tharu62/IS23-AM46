package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class P_CARD_LOGICTest {

    @Test
    void testCheckCardLogic() {
        BOOKSHELF bookshelf = new BOOKSHELF();
        P_CARD_LOGIC card = new P_CARD_LOGIC_1();
        bookshelf.Grid[0][0] = item.PLANTS;
        assertEquals(1, card.checkCardLogic(bookshelf));
        bookshelf.Grid[1][4] = item.CATS;
        assertEquals(2, card.checkCardLogic(bookshelf));
        bookshelf.Grid[0][2] = item.BOOKS;
        assertEquals(2, card.checkCardLogic(bookshelf));
    }
}