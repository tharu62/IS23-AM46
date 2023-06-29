package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class DRAW extends TOKEN_GENERATOR {
    public List<COMMON_GOAL_CARD> card = new ArrayList<>();
    CARD_LOGIC_GENERATOR cardGenerator = new CARD_LOGIC_GENERATOR();
    TOKEN_GENERATOR tokenGenerator = new TOKEN_GENERATOR();

    /**
     * This method sets the card logic and token value of the Common Goal Cards.
     *
     * @param playerNumber is the number of player in the lobby.
     */
    public void setFirstDraw(int playerNumber){
        tokenGenerator.player_number = playerNumber;
        COMMON_GOAL_CARD card1 = new COMMON_GOAL_CARD(), card2 = new COMMON_GOAL_CARD();
        card1.SetCardLogic(cardGenerator.SetCardLogic());
        card.add(card1);
        card.get(0).SetToken(tokenGenerator.setTokenLogic());

        card2.SetCardLogic(cardGenerator.SetCardLogic());
        card.add(card2);
        card.get(1).SetToken(tokenGenerator.setTokenLogic());
    }

    /**
     * This method checks if the Common Goals have been achieved.
     * If the goal has been achieved the tokens are updated and the score is returned.
     *
     * @param bookshelf is the bookshelf od the player to check.
     * @param goal1reached is a boolean that tels the method if the player has already achieved that goal.
     * @param goal2reached is a boolean that tels the method if the player has already achieved that goal.
     * @return
     */
    public int check(BOOKSHELF bookshelf, boolean goal1reached, boolean goal2reached){
        int temp = 0;
        if(!goal1reached && card.get(0).check(bookshelf)){
            temp += card.get(0).token_value;
            card.get(0).token_value = card.get(0).token_value + card.get(0).token.UpdateValue(card.get(0).token_value);
        }
        if(!goal2reached && card.get(1).check(bookshelf)){
            temp += card.get(1).token_value;
            card.get(1).token_value = card.get(1).token_value + card.get(1).token.UpdateValue(card.get(1).token_value);
        }
        return temp;
    }

}
