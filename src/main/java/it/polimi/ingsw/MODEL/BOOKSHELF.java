package it.polimi.ingsw.MODEL;

public class BOOKSHELF {
    item[][] Grid= new item[6][5];
    boolean IsFull=false;
    item[] itemToPut= new item[3];

    public void putItems(int m,int a, int b, int c){
        Grid[a][m]=itemToPut[0];
        Grid[b][m]=itemToPut[1];
        Grid[c][m]=itemToPut[2];
    }

}
