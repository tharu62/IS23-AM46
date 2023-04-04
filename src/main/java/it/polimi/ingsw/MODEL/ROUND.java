package it.polimi.ingsw.MODEL;

public class ROUND {
    TURN turn= new TURN();
    boolean last=false;
    int count=0;
    int roundTrip=0;

    public void set(int roundTrip){
        this.roundTrip=roundTrip;
    }

    public void update(BOOKSHELF bookshelf){
        if(bookshelf.IsFull){
            this.last=true;
        }
        if(turn.count==(roundTrip-1)){
            count++;
            turn.update();
        }
        else{
            turn.count++;
        }
    }

}
