package it.polimi.ingsw.MODEL;

public class BOOKSHELF {
    item[][] Grid= new item[6][5];
    boolean IsFull=false;
    item[] itemToPut= new item[3];
    int a;

    public void putItems(int m,int a, int b, int c){
        Grid[a][m]=itemToPut[0];
        Grid[b][m]=itemToPut[1];
        Grid[c][m]=itemToPut[2];
    }

    public  int checkAdjacentItem(item comp){
        int score=0;
        int temp=0;
        for(int i=0; i<Grid.length ; i++){
            for(int j=0; j<Grid[j].length ;j++){
                if(Grid[i][j]==comp){
                    temp=dfs(i,j,comp);
                    if(temp>=3){
                        score+=temp;
                    }
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
        if(Grid[i][j+1]==comp){
            Grid[i][j+1]=item.EMPTY;
            score+= 1+dfs(i,j+1,comp);
        }
        if(Grid[i+1][j]==comp){
            Grid[i+1][j]=item.EMPTY;
            score+= 1+dfs(i+1,j,comp);
        }
        if(Grid[i][j-1]==comp){
            Grid[i][j-1]=item.EMPTY;
            score+= 1+dfs(i,j-1,comp);
        }
        if(Grid[i-1][j]==comp){
            Grid[i-1][j]=item.EMPTY;
            score+= 1+dfs(i-1,j,comp);
        }
        Grid[i][j]=item.EMPTY;
        return score;
    }
}
