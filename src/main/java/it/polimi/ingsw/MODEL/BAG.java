package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BAG {
    List<item> itemList= new ArrayList<>(132);
    int CATS=0;
    int BOOKS=0;
    int GAMES=0;
    int FRAMES=0;
    int TROPHIES=0;
    int PLANTS=0;

    public void setItemList(){
        Random rand = new Random();
        int upperbound = 7, i;
        for(int j=0; j<132; j++) {
            do {
               i = rand.nextInt(upperbound);
            } while (i == 0);
            int l=0;

            while(l==0){
                switch (i) {
                    case(1):
                        if(this.CATS < 22){
                            this.itemList.add(item.CATS);
                            this.CATS++;
                            l++;
                            break;
                        } else {
                            i++;
                            continue;
                        }
                    case(2):
                        if(this.GAMES < 22){
                            this.itemList.add(item.GAMES);
                            this.GAMES++;
                            l++;
                            break;
                        } else {
                            i++;
                            continue;
                        }
                    case(3):
                        if(this.BOOKS < 22){
                            this.itemList.add(item.BOOKS);
                            this.BOOKS++;
                            l++;
                            break;
                        } else {
                            i++;
                            continue;
                        }
                    case(4):
                        if(this.FRAMES < 22){
                            this.itemList.add(item.FRAMES);
                            this.FRAMES++;
                            l++;
                            break;
                        } else {
                            i++;
                            continue;
                        }
                    case(5):
                        if(this.TROPHIES < 22){
                            this.itemList.add(item.TROPHIES);
                            this.TROPHIES++;
                            l++;
                            break;
                        } else {
                            i++;
                            continue;
                        }
                    case(6):
                        if(this.PLANTS < 22){
                            this.itemList.add(item.PLANTS);
                            this.PLANTS++;
                            l++;
                            break;
                        } else {
                            i = 1;
                        }
                }
            }
        }
    }

    public item draw(){
        return itemList.get(itemList.size()-1);
    }

}
