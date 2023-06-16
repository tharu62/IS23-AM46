package it.polimi.ingsw.VIEW.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;


public class StandardSprite {
    Sprite[][] Board = new Sprite[9][9];
    public Sprite[][] setBoard(GridPane gridPane){
        int count = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j < 9; j++){
                if(gridPane.getChildren().get(count) instanceof ImageView){
                    Board[i][j] = new Sprite(i,j, (ImageView) gridPane.getChildren().get(count));
                    System.out.println(count);
                    count++;
                }
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
