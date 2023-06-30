package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class StandardSpriteDataStructure {
    Sprite[][] Board = new Sprite[9][9];
    Sprite[][] Bookshelf = new Sprite[6][5];

    /**
     * This method initialize a bi-dimensional array of Sprites Board (int the GUI) with the ImageViews
     * contained in the given GridPane.
     * It is required that the ImageViews in the GridPane are inserted in a specific order in the GridPane
     * in the construction of the scene ( in sceneBuilder or directly in the fxml file ).
     *
     * @return the initialized array of Sprites.
     */
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

    /**
     * This method initialize an array od Sprites with the given ImageVies
     *
     * @param a is an ImageView
     * @param b is an ImageView
     * @param c is an ImageView
     * @return
     */
    public Sprite[] setDrawPile(ImageView a, ImageView b, ImageView c){
        Sprite[] drawpile = new Sprite[3];
        drawpile[0] = new Sprite(a);
        drawpile[1] = new Sprite(b);
        drawpile[2] = new Sprite(c);
        return drawpile;
     }

    /**
     * This method initialize a given array of Strings with a pre-defined set of characters.
     *
     * @param pile is the give array.
     */
    public void setDrawPileOrder(String[] pile){
        pile[0] = "c";
        pile[1] = "b";
        pile[2] = "a";
    }

    /**
     * This method initialize the bi-dimensional array of Sprites Bookshelf (int the GUI) with the ImageViews
     * contained in the given GridPane.
     * It is required that the ImageViews in the GridPane are inserted in a specific order in the GridPane
     * in the construction of the scene ( in sceneBuilder or directly in the fxml file ).
     *
     * @param gridPane
     * @return
     */
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
