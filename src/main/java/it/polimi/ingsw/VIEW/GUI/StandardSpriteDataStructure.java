package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class StandardSpriteDataStructure {
    Sprite[][] Board = new Sprite[9][9];
    Sprite[][] Bookshelf = new Sprite[6][5];

    public Sprite[][] setBoard(GridPane gridPane){
        int count = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j < 9; j++){
                if(gridPane.getChildren().get(count) instanceof ImageView){
                    Board[i][j] = new Sprite(i,j, (ImageView) gridPane.getChildren().get(count));
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

    public void setDrawPileOrder(String[] pile){
        pile[0] = "a";
        pile[1] = "b";
        pile[2] = "c";
    }

    public Sprite[][] setBookshelf(GridPane gridPane){
        int count = 0;
        for(int i=0; i<6; i++){
            for(int j=0; j < 5; j++){
                if(gridPane.getChildren().get(count) instanceof ImageView){
                    Bookshelf[i][j] = new Sprite(i,j, (ImageView) gridPane.getChildren().get(count));
                    count++;
                }
            }
        }
        return Bookshelf;
    }
}