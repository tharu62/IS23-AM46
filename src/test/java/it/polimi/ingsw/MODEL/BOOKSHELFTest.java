package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BOOKSHELFTest {

    @Test
    void testPutItems() {
        BOOKSHELF bookshelf = new BOOKSHELF();
        bookshelf.itemToPut.add(item.CATS);
        bookshelf.itemToPut.add(item.TROPHIES);
        bookshelf.itemToPut.add(item.PLANTS);
        bookshelf.putItems(2, 1, 2, 0);
        assertEquals(bookshelf.Grid[5][2], item.PLANTS);
        assertEquals(bookshelf.Grid[4][2], item.CATS);
        assertEquals(bookshelf.Grid[3][2], item.TROPHIES);
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