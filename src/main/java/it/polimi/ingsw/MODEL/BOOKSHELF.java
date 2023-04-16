package it.polimi.ingsw.MODEL;

public class BOOKSHELF {
    item[][] Grid = {{item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY},
                     {item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY, item.EMPTY}};
    boolean IsFull=false;
    item[] itemToPut= new item[3];
    int itemsInGrid=0;

    public void putItems(int m,int a, int b, int c){
        for(int j=0; j<3; j++){
            if (j==a){
                Grid[firstFreeRow(m)][m]=itemToPut[0];
                itemToPut[0]=null;
                this.itemsInGrid++;
            }
            if(j==b){
                Grid[firstFreeRow(m)][m]=itemToPut[1];
                itemToPut[1]=null;
                this.itemsInGrid++;
            }
            if (j==c){
                Grid[firstFreeRow(m)][m]=itemToPut[2];
                itemToPut[2]=null;
                this.itemsInGrid++;
            }
        }

        if(itemsInGrid==30){
            this.IsFull=true;
        }
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
