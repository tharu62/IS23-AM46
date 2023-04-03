package it.polimi.ingsw.MODEL;

public class TOKEN_GENERATOR {
    int player_number;
    public TOKEN setTokenLogic(){
        if(player_number==2){
            return new TWO_PLAYERS_LOGIC();
        }
        if(player_number==3){
            return new THREE_PLAYERS_LOGIC();
        }
        return new FOUR_PLAYERS_LOGIC();
    }
}
