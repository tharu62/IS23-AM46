package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GAMETest extends TestCase {

    @Test
    void testAddPlayer() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        assertEquals(2, game.playerNumber);
        game.addPlayer("Chiara");
        assertEquals(3, game.playerNumber);
    }

    @Test
    void testSetBoard() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        game.setBoard();
        assertEquals(game.space.board.Grid[0][0], item.EMPTY);
        assertFalse(game.space.board.Grid[1][3].equals(item.EMPTY));
        assertEquals(game.space.board.Grid[0][3], item.EMPTY);
        assertEquals(game.space.board.Grid[0][4], item.EMPTY);
    }



    @Test
    void testDrawCommonGoalCards() {

    }

    @Test
    void testDrawPersonalGoalCards() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.addPlayer("Davide");
        game.DrawPersonalGoalCards();
        HashSet<PERSONAL_GOAL_CARD> personalGoalCards = new HashSet<>();
        for (int i = 0; i < game.playerNumber; i++) {
            personalGoalCards.add(game.space.player.get(i).personal);
        }
        assertEquals(game.playerNumber, personalGoalCards.size());
    }

    @Test
    void testChooseFirstPlayerSeat() {
        GAME game = new GAME();
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.ChooseFirstPlayerSeat();
        assertTrue(game.playerToPlay.equals("Antonio") || game.playerToPlay.equals("Bruno") || game.playerToPlay.equals("Chiara"));
    }

    void testMasterStartTurn() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.playerToPlay = "Alberto";

    }

    @Test
    void testPlayerDrawItem() {
        GAME game= new GAME();
        game.addPlayer("Giovanni");
        game.addPlayer("Antonio");
        game.addPlayer("Elisa");
        game.playerToPlay = "Giovanni";
        game.setBoard();
        assertTrue(game.playerDrawItem("Giovanni", 0, 3));
        assertFalse(game.playerDrawItem("Antonio", 1, 3));
        assertTrue(game.playerDrawItem("Giovanni", 1, 3));
    }

    @Test
    void testPlayerPutItems() {
        GAME game= new GAME();
        game.addPlayer("Giovanni");
        game.addPlayer("Antonio");
        game.setBoard();
        game.playerToPlay = "Giovanni";
        game.playerDrawItem("Giovanni", 1, 3);
        game.playerDrawItem("Giovanni", 1, 4);
        assertEquals(game.space.player.get(0).bookshelf.Grid[5][1], item.EMPTY);
        game.playerPutItems("Giovanni", 1, 0, 1, -1);
        assertNotEquals(game.space.player.get(0).bookshelf.Grid[5][1], item.EMPTY);
    }

    @Test
    void testPlayerWantsToCheckScore() {
        GAME game = new GAME();
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.playerToPlay = "Antonio";
        game.DrawCommonGoalCards();
        game.PlayerWantsToCheckScore("Antonio");
        assertEquals(0, game.space.player.get(0).score);

        game.space.player.get(0).bookshelf.Grid[0][0] = item.FRAMES;
        game.space.player.get(0).bookshelf.Grid[5][4] = item.FRAMES;
        game.space.player.get(0).bookshelf.Grid[5][0] = item.FRAMES;
        game.space.player.get(0).bookshelf.Grid[0][4] = item.FRAMES;
        game.PlayerWantsToCheckScore("Antonio");
        assertEquals(8, game.space.player.get(0).score);
    }
}