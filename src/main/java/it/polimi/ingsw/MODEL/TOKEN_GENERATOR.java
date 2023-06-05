package it.polimi.ingsw.MODEL;

public class TOKEN_GENERATOR {
    int player_number;

    /**
     * This method sets the logic to update the token value in each common goal card.
     * @return TOKEN is a logic (essentially a method) that decreases its token value depending on the number of players.
     */
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
