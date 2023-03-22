package it.polimi.ingsw.MODEL;

public class DRAW {
    GOAL_CARD card= new COMMON_GOAL_CARD();
    CARD_LOGIC_GENERATOR cardGenerator= new CARD_LOGIC_GENERATOR();
    TOKEN_GENERATOR tokenGenerator= new TOKEN_GENERATOR();
    public void setFirstDraw(int playerNumber){
        tokenGenerator.player_number=playerNumber;
        card.SetCardLogic(cardGenerator.SetCardLogic());
        card.SetToken(tokenGenerator.setTokenLogic());
    }
    public int check(BOOKSHELF bookshelf){
        /**
         *
         */
        return 1;
    }

}
