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
            for(int i=0; i < 3; i++){
                if(pile[i].equals("a")){
                    switch (i){
                        case 2:
                            i=0;
                            break;
                        case 0:
                            i=2;
                            break;

                    }
                    return i;
                }
            }
        }
        if(fxid.getId().equals("secondDraw")){
            for(int i=0; i < 3; i++){
                if(pile[i].equals("b")){
                    switch (i){
                        case 2:
                            i=0;
                            break;
                        case 0:
                            i=2;
                            break;

                    }
                    return i;
                }
            }
        }
        if(fxid.getId().equals("thirdDraw")){
            for(int i=0; i < 3; i++){
                if(pile[i].equals("c")){
                    switch (i){
                        case 2:
                            i=0;
                            break;
                        case 0:
                            i=2;
                            break;

                    }
                    return i;
                }
            }
        }
        return -1;
    }
}
