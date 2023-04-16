package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BOOKSHELFTest {

    @Test
    void testPutItems() {
        BOOKSHELF bookshelf = new BOOKSHELF();
        bookshelf.itemToPut[0] = item.CATS;
        bookshelf.itemToPut[1] = item.TROPHIES;
        bookshelf.itemToPut[2] = item.PLANTS;
        bookshelf.putItems(2, 1, 2, 0);
        assertTrue(bookshelf.Grid[5][2].equals(item.PLANTS));
        assertTrue(bookshelf.Grid[4][2].equals(item.CATS));
        assertTrue(bookshelf.Grid[3][2].equals(item.TROPHIES));
        assertEquals(3, bookshelf.itemsInGrid);
    }

    @Test
    void testCheckAdjacentItem() {
        BOOKSHELF bookshelf = new BOOKSHELF();
        bookshelf.Grid[5][0] = item.TROPHIES;
        bookshelf.Grid[5][1] = item.TROPHIES;
        bookshelf.Grid[5][2] = item.TROPHIES;
        bookshelf.Grid[4][2] = item.TROPHIES;

        bookshelf.Grid[5][4] = item.TROPHIES;
        bookshelf.Grid[4][4] = item.TROPHIES;
        bookshelf.Grid[3][4] = item.TROPHIES;
        assertEquals(5, bookshelf.checkAdjacentItem(item.TROPHIES));
        assertEquals(0, bookshelf.checkAdjacentItem(item.GAMES));
    }
}