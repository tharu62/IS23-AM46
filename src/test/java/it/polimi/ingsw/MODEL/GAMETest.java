package it.polimi.ingsw.MODEL;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class GAMETest extends TestCase {

    @Test
    public void testAddPlayer() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        assertEquals(2, game.space.player.size());
        game.addPlayer("Chiara");
        assertEquals(3, game.space.player.size());
    }

    @Test
    public void testSetBoard() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        game.setBoard();
        assertEquals(game.space.board.Grid[0][0], item.EMPTY);
        assertNotEquals(game.space.board.Grid[1][3], item.EMPTY);
        assertEquals(game.space.board.Grid[0][3], item.EMPTY);
        assertEquals(game.space.board.Grid[0][4], item.EMPTY);
    }



    @Test
    public void testDrawCommonGoalCards() {
        GAME game = new GAME();
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.DrawCommonGoalCards();
        assertNotEquals(game.master.FirstDraw.card.get(0), game.master.FirstDraw.card.get(1));
    }

    @Test
    public void testDrawPersonalGoalCards() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.addPlayer("Davide");
        game.DrawPersonalGoalCards();
        HashSet<PERSONAL_GOAL_CARD> personalGoalCards = new HashSet<>();
        for (int i = 0; i < game.space.player.size(); i++) {
            personalGoalCards.add(game.space.player.get(i).personal);
        }
        assertEquals(game.space.player.size(), personalGoalCards.size());
    }

    @Test
    public void testChooseFirstPlayerSeat() {
        GAME game = new GAME();
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.ChooseFirstPlayerSeat();
        assertTrue(game.playerToPlay.equals("Antonio") || game.playerToPlay.equals("Bruno") || game.playerToPlay.equals("Chiara"));
    }

    public void testMasterStartTurn() {
        GAME game = new GAME();
        game.addPlayer("Alberto");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.playerToPlay = "Alberto";

    }

    @Test
    public void testPlayerDrawItem() {
        GAME game= new GAME();
        game.addPlayer("Giovanni");
        game.addPlayer("Antonio");
        game.addPlayer("Elisa");
        game.playerToPlay = "Giovanni";
        game.setBoard();
        assertFalse(game.playerDrawItem("Giovanni", 0, 3));
        assertFalse(game.playerDrawItem("Antonio", 1, 3));
        assertTrue(game.playerDrawItem("Giovanni", 1, 3));
    }

    @Test
    public void testPlayerPutItems() {
        GAME game= new GAME();
        game.addPlayer("Giovanni");
        game.addPlayer("Antonio");
        game.setBoard();
        game.playerToPlay = "Giovanni";
        game.playerDrawItem("Giovanni", 1, 3);
        game.playerDrawItem("Giovanni", 1, 4);
        assertEquals(game.space.player.get(0).bookshelf.Grid[5][1], item.EMPTY);
        assertTrue(game.playerPutItems("Giovanni", 1, 0, 1, -1));
        assertNotEquals(game.space.player.get(0).bookshelf.Grid[5][1], item.EMPTY);
        assertNotEquals(game.space.player.get(0).bookshelf.Grid[4][1], item.EMPTY);
    }

    @Test
    public void testPlayerWantsToCheckScore() {
        GAME game = new GAME();
        TOKEN_GENERATOR tokenGenerator = new TOKEN_GENERATOR();
        tokenGenerator.player_number = 2;
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.playerToPlay = "Antonio";
        COMMON_GOAL_CARD card = new COMMON_GOAL_CARD(), card1 = new COMMON_GOAL_CARD();
        card.cardLogic = new CARD_LOGIC_9();
        card1.cardLogic = new CARD_LOGIC_1();
        card.SetToken(tokenGenerator.setTokenLogic());
        card1.SetToken(tokenGenerator.setTokenLogic());
        game.master.FirstDraw.card.add(0, card);
        game.master.FirstDraw.card.add(card1);
        game.PlayerWantsToCheckScore("Antonio");
        assertEquals(0, game.space.player.get(0).score);

        int cont = 0;
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                if (cont < 8) {
                    game.space.player.get(0).bookshelf.Grid[i][j] = item.FRAMES;
                    cont++;
                } else break;
            }
        }
        game.PlayerWantsToCheckScore("Antonio");
        assertEquals(8, game.space.player.get(0).score);

        game.playerToPlay = "Bruno";
        cont = 0;
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                if (cont < 8) {
                    game.space.player.get(1).bookshelf.Grid[i][j] = item.GAMES;
                    cont++;
                } else break;
            }
        }
        game.PlayerWantsToCheckScore("Bruno");
        assertEquals(4, game.space.player.get(1).score);
    }

    @Test
    public void testMasterEndTurn() {
        GAME game = new GAME();
        game.addPlayer("Antonio");
        game.addPlayer("Bruno");
        game.addPlayer("Chiara");
        game.addPlayer("Davide");
        game.setBoard();
        game.playerToPlay = "Bruno";
        assertFalse(game.masterEndTurn("Antonio"));
        assertTrue(game.masterEndTurn("Bruno"));

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (game.space.board.Grid[i][j] != item.EMPTY) game.space.board.Grid[i][j] = item.OBJECT;
            }
        }
        assertTrue(game.space.board.IsToBeRestored());
        assertTrue(game.masterEndTurn("Bruno"));
        assertFalse(game.space.board.IsToBeRestored());
    }
}