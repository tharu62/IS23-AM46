package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.image.ImageView;

public class SearchIndex {
    public int findRowFromBoard(Sprite[][] Board, ImageView fxid){
        for(int i =0; i < Board.length; i++ ){
            for(int j = 0; j < Board.length; j++){
                if(Board[i][j].fxid == fxid ){
                    return i;
                }
            }
        }
        return -1;
    }

    public int findColFromBoard(Sprite[][] Board, ImageView fxid){
        for(int i =0; i < Board.length; i++ ){
            for(int j = 0; j < Board.length; j++){
                if(Board[i][j].fxid == fxid ){
                    return j;
                }
            }
        }
        return -1;
    }

    public int findIndexFromPile(String[] pile, ImageView fxid){
        if(fxid.getId().equals("firstDraw")){
            for(int i=0; i < pile.length; i++){
                if(pile[i].equals("a")){
                    return i;
                }
            }
        }
        if(fxid.getId().equals("secondDraw")){
            for(int i=0; i < pile.length; i++){
                if(pile[i].equals("b")){
                    return i;
                }
            }
        }
        if(fxid.getId().equals("thirdDraw")){
            for(int i=0; i < pile.length; i++){
                if(pile[i].equals("c")){
                    return i;
                }
            }
        }
        return -1;
    }
}
