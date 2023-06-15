package it.polimi.ingsw.MODEL;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BOARDTest{

    @Test
    void testSetBoardTwoPlayers() {
        BOARD board = new BOARD();
        board.setGrid(2);
        int cont = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if (!board.Grid[i][j].equals(item.EMPTY)) cont++;
            }
        }
        assertEquals(29, cont);
        assertEquals(board.Grid[0][0], item.EMPTY);
        assertNotEquals(board.Grid[1][3], item.EMPTY);
        assertEquals(board.Grid[0][3], item.EMPTY);
        assertEquals(board.Grid[0][4], item.EMPTY);
    }
    @Test
    void testSetBoardThreePlayers() {
        BOARD board = new BOARD();
        board.setGrid(3);
        int cont = 0;
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++) {
                if (!board.Grid[i][j].equals(item.EMPTY)) cont++;
            }
        }
        assertEquals(37, cont);
        assertEquals(board.Grid[0][0], item.EMPTY);
        assertNotEquals(board.Grid[1][3], item.EMPTY);
        assertNotEquals(board.Grid[0][3], item.EMPTY);
        assertEquals(board.Grid[0][4], item.EMPTY);
    }

    @Test
    void testSetBoardFourPlayers() {
        BOARD board = new BOARD();
        board.setGrid(4);
        int cont = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board.Grid[i][j].equals(item.EMPTY)) cont++;
            }
        }
        assertEquals(45, cont);
        assertEquals(board.Grid[0][0], item.EMPTY);
        assertNotEquals(board.Grid[1][3], item.EMPTY);
        assertNotEquals(board.Grid[0][3], item.EMPTY);
        assertNotEquals(board.Grid[0][4], item.EMPTY);
    }
    @Test
    void testDrawItem(){
        BOARD board = new BOARD();
        board.setGrid(2);
        assertEquals(item.EMPTY, board.drawItem(0, 0));
        assertNotEquals(item.EMPTY, board.drawItem(1, 3));
        assertNotEquals(item.OBJECT, board.drawItem(1, 3));
        assertEquals(item.EMPTY, board.drawItem(5, 1));
        assertNotEquals(item.EMPTY, board.drawItem(1, 4));
        assertEquals(item.EMPTY, board.drawItem(2, 4));
    }

    @Test
    void testIsToBeRestored() {
        BOARD board = new BOARD();
        assertTrue(board.IsToBeRestored());
        board.setGrid(3);
        assertFalse(board.IsToBeRestored());
    }
}