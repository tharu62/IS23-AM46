package it.polimi.ingsw.MODEL;

abstract class GOAL_CARD {
    CARD_LOGIC cardLogic;
    TOKEN token;
    int token_value;
    abstract void SetCardLogic(CARD_LOGIC logic);
    abstract void SetToken(TOKEN token);
    abstract boolean check(BOOKSHELF bookshelf);
}
class COMMON_GOAL_CARD extends GOAL_CARD {
    CARD_LOGIC cardLogic;
    TOKEN token;
    int token_value=8;
    public void SetCardLogic(CARD_LOGIC logic){
        this.cardLogic= logic;
    }
    public void SetToken(TOKEN token){
        this.token=token;
    }
    public boolean check(BOOKSHELF bookshelf){
        return this.cardLogic.CheckCardLogic(bookshelf);
    }
}
class PERSONAL_GOAL_CARD extends GOAL_CARD{
    CARD_LOGIC cardLogic;
    int token_value;
    public void SetCardLogic(CARD_LOGIC logic){
        this.cardLogic= logic;
    }
    public void SetToken(TOKEN token){

    }
    public boolean check(BOOKSHELF bookshelf){

        return this.cardLogic.CheckCardLogic(bookshelf);
    }

    public int UpdateValue(BOOKSHELF bookshelf){
        return 4;
    }
}
