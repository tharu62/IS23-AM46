package it.polimi.ingsw.MODEL;

import java.util.ArrayList;
import java.util.List;

public class BOOKSHELF {
    item[][] Grid = {{item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY}};
    boolean IsFull=false;
    List<item> itemToPut= new ArrayList<item>();
    int itemsInGrid=0;

    public boolean putItems(int m,int a, int b, int c){
        if(firstFreeRow(m) < (itemToPut.size() - 1)){
            return false;
        }
        for(int j=0; j<3; j++){
            if (j==a){
                Grid[firstFreeRow(m)][m]=itemToPut.get(0);
                this.itemsInGrid++;
            }
            if(j==b){
                Grid[firstFreeRow(m)][m]=itemToPut.get(1);
                this.itemsInGrid++;
            }
            if (j==c){
                Grid[firstFreeRow(m)][m]=itemToPut.get(2);
                this.itemsInGrid++;
            }
        }
        itemToPut.clear();
        if(itemsInGrid==30){
            this.IsFull=true;
        }
        return true;
    }

    public  int checkAdjacentItem(item comp){
        int score=0;
        int temp;
        for(int i=0; i<Grid.length ; i++){
            for(int j=0; j<Grid[j].length ;j++){
                if(Grid[i][j]==comp){
                    temp=dfs(i,j,comp);
                    if (temp == 3) score += 2;
                    if (temp == 4) score += 3;
                    if (temp == 5) score += 5;
                    if (temp > 5) score += 8;
                }
            }
        }
        return score;
    }

    private int dfs(int i, int j, item comp){
        int score=0;
        if(i<0 || j<0 || i>Grid.length || j>Grid.length){
            return 0;
        }
        if(j < 4 && Grid[i][j+1]==comp){
            Grid[i][j+1]=item.EMPTY;
            score+= 1+dfs(i,j+1,comp);
        }
        if(i < 5 && Grid[i+1][j]==comp){
            Grid[i+1][j]=item.EMPTY;
            score+= 1+dfs(i+1,j,comp);
        }
        if(j > 0 && Grid[i][j-1]==comp){
            Grid[i][j-1]=item.EMPTY;
            score+= 1+dfs(i,j-1,comp);
        }
        if(i > 0 && Grid[i-1][j]==comp){
            Grid[i-1][j]=item.EMPTY;
            score+= 1+dfs(i-1,j,comp);
        }
        Grid[i][j]=item.EMPTY;
        return score;
    }

    public item[][] getGrid() {
        return Grid;
    }

    private int firstFreeRow(int x) {
        int i=5;
        while (!this.Grid[i][x].equals(item.EMPTY)) {
            i--;
        }
        return i;
    }
}
