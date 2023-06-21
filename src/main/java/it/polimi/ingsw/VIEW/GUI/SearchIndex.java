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

    public int findIndexFromPile(Sprite[] pile, ImageView fxid){
        for(int i=0; i < pile.length; i++){
            if(pile[i].fxid == fxid){
                return i;
            }
        }
        return -1;
    }
}
