package it.polimi.ingsw.MODEL;

public class ROUND {
    public TURN turn= new TURN();
    public boolean last = false;
    public int count = 0;
    int roundTrip = 0;

    /**
     * This method sets the correct number of turns in each round.
     * @param roundTrip is the number of players in the match but also the number of turn in each round.
     */
    public void set(int roundTrip){
        this.roundTrip = roundTrip;
    }

    /**
     * This method checks the bookshelf given as argument, if it's full then the attribute boolean last is set to false.
     * If it's the last turn of the round, the turn count is resetted to '0', else the turn count is incremented by 1.
     * @param bookshelf is the bookshelf of a player.
     */
    public void update(BOOKSHELF bookshelf){
        if(bookshelf.IsFull){
            this.last = true;
        }
        if(turn.count == (roundTrip)){
            turn.reset();
        }
        else{
            turn.count++;
        }
    }

}
