package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class DRAW extends TOKEN_GENERATOR {
    public List<COMMON_GOAL_CARD> card= new ArrayList<COMMON_GOAL_CARD>();
    CARD_LOGIC_GENERATOR cardGenerator= new CARD_LOGIC_GENERATOR();
    TOKEN_GENERATOR tokenGenerator= new TOKEN_GENERATOR();

    public void setFirstDraw(int playerNumber){
        tokenGenerator.player_number=playerNumber;
        card.get(1).SetCardLogic(cardGenerator.SetCardLogic());
        card.get(1).SetToken(tokenGenerator.setTokenLogic());

        card.get(2).SetCardLogic(cardGenerator.SetCardLogic());
        card.get(2).SetToken(tokenGenerator.setTokenLogic());
    }

    public int check(BOOKSHELF bookshelf){
        int temp;
        if(card.get(1).check(bookshelf)){
            temp=card.get(1).token_value;
            card.get(1).token.UpdateValue(card.get(1).token_value);
            return temp;
        }
        if(card.get(2).check(bookshelf)){
            temp=card.get(2).token_value;
            card.get(2).token.UpdateValue(card.get(2).token_value);
            return temp;
        }
        return 0;
    }

}
