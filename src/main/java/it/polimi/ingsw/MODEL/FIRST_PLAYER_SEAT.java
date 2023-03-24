package it.polimi.ingsw.MODEL;

import java.util.List;
import java.util.Random;

public class FIRST_PLAYER_SEAT {
    int n;
    String username;

    public String choose(List<PLAYER> list){
        Random rand = new Random();
        int upperbound = list.size();
        this.n = rand.nextInt(upperbound);
        this.username=list.get(n).username;
        return list.get(n).username;
    }

}
