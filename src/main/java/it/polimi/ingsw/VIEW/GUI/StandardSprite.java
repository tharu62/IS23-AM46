package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


public class StandardSprite {
    Sprite[][] Board = new Sprite[9][9];
    public Sprite[][] setBoard(GridPane gridPane){
        int count=0;
        for(int i=0; i<9; i++){
            for(int j=0; j < 9; j++){
                //Board[i][j] = new Sprite(i,j, ((ImageView) gridPane.getChildren().get(i)));
                count++;
            }
        }
        return Board;
    }

    public Sprite[] setDrawPile(ImageView a, ImageView b, ImageView c){
        Sprite[] drawpile = new Sprite[3];
        drawpile[0] = new Sprite(a);
        drawpile[1] = new Sprite(b);
        drawpile[2] = new Sprite(c);
        return drawpile;
     }

}
