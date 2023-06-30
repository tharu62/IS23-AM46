package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//This class tests the methods of the BOOKSHELF class
class BOOKSHELFTest {

    @Test
    void testPutItems() {
        //It tests the method putItems() and verifies that the attribute IsFull is set correctly
        BOOKSHELF bookshelf = new BOOKSHELF();
        bookshelf.itemToPut.add(item.CATS);
        bookshelf.itemToPut.add(item.TROPHIES);
        bookshelf.itemToPut.add(item.PLANTS);
        assertTrue(bookshelf.putItems(2, 1, 2, 0));
        assertEquals(bookshelf.Grid[5][2], item.PLANTS);
        assertEquals(bookshelf.Grid[4][2], item.CATS);
        assertEquals(bookshelf.Grid[3][2], item.TROPHIES);
        assertEquals(3, bookshelf.itemsInGrid);
        assertFalse(bookshelf.putItems(1, 2, 1, 0));
        bookshelf.Grid[2][2] = item.BOOKS;
        bookshelf.itemToPut.add(item.CATS);
        bookshelf.itemToPut.add(item.TROPHIES);
        bookshelf.itemToPut.add(item.PLANTS);
        assertFalse(bookshelf.putItems(2, 0, 1, 2));
        assertTrue(bookshelf.putItems(1, 0, 2, 1));
        bookshelf.Grid[1][2] = item.BOOKS;
        bookshelf.Grid[0][2] = item.BOOKS;
        bookshelf.itemToPut.add(item.CATS);
        assertFalse(bookshelf.putItems(2, 0, -1, -1));
        bookshelf.itemsInGrid = 29;
        assertTrue(bookshelf.putItems(0, 0, -1, -1));
        assertTrue(bookshelf.IsFull);
    }

    @Test
    void testCheckAdjacentItem() {
        //It tests the method checkAdjacentItem() and the private method dfs()
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