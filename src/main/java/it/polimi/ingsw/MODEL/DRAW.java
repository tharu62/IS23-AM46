package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class DRAW extends TOKEN_GENERATOR {
    public List<COMMON_GOAL_CARD> card= new ArrayList<>();
    CARD_LOGIC_GENERATOR cardGenerator= new CARD_LOGIC_GENERATOR();
    TOKEN_GENERATOR tokenGenerator= new TOKEN_GENERATOR();

    public void setFirstDraw(int playerNumber){
        tokenGenerator.player_number=playerNumber;
        COMMON_GOAL_CARD card1 = new COMMON_GOAL_CARD(), card2 = new COMMON_GOAL_CARD();
        card1.SetCardLogic(cardGenerator.SetCardLogic());
        card.add(card1);
        card.get(0).SetToken(tokenGenerator.setTokenLogic());

        card2.SetCardLogic(cardGenerator.SetCardLogic());
        card.add(card2);
        card.get(1).SetToken(tokenGenerator.setTokenLogic());
    }

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
