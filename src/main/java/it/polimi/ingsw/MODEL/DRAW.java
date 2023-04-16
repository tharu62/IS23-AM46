package it.polimi.ingsw.MODEL;

public class DRAW extends TOKEN_GENERATOR {
    COMMON_GOAL_CARD card= new COMMON_GOAL_CARD();
    CARD_LOGIC_GENERATOR cardGenerator= new CARD_LOGIC_GENERATOR();
    TOKEN_GENERATOR tokenGenerator= new TOKEN_GENERATOR();

    public void setFirstDraw(int playerNumber){
        tokenGenerator.player_number=playerNumber;
        card.SetCardLogic(cardGenerator.SetCardLogic());
        card.SetToken(tokenGenerator.setTokenLogic());
    }

    public int check(BOOKSHELF bookshelf){
        int temp;
        if(card.check(bookshelf)){
            temp=card.token_value;
            card.token.UpdateValue(card.token_value);
            return temp;
        }
        return 0;
    }

}
