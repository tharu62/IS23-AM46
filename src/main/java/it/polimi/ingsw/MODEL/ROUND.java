package it.polimi.ingsw.MODEL;

public class ROUND {
    public TURN turn= new TURN();
    public boolean last = false;
    public int count = 0;
    int roundTrip = 0;

    public void set(int roundTrip){
        this.roundTrip = roundTrip;
    }

    public void update(BOOKSHELF bookshelf){
        if(bookshelf.IsFull){
            this.last=true;
        }
        if(turn.count == (roundTrip)){
            turn.update();
        }
        else{
            turn.count++;
        }
    }

}
