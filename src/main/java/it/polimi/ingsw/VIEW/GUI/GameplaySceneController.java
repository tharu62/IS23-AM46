package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.item;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class GameplaySceneController {
    @FXML
    public GridPane gridPane;
    @FXML
    public ImageView t341;
    @FXML
    public ImageView t342;
    @FXML
    public ImageView t343;
    int i=0;
    public boolean drawInProgress = false;
    public int drawCounter = 0;
    public boolean putInProgress = false;
    public Sprite selectedItem = new Sprite(null);
    public Sprite[][] SpritesBoard = new StandardSprite().setBoard(gridPane);
    public Sprite[] DrawPile = new Sprite[3];
    public Sprite[][] SpriteBookshelf = new Sprite[6][5];

    public void ButtonCLick(MouseEvent mouseEvent) {
        if(i==0){
            updateSlotGames((ImageView) gridPane.getChildren().get(0));
            i++;
        }else{
            updateSlotBooks((ImageView) gridPane.getChildren().get(0));
            i=0;
        }
    }

    private void updateSlotCats(ImageView imageViewID){
        imageViewID.setImage(new Image("Gatti1.1.png"));
        System.out.println("button clicked");
    }

    private void updateSlotGames(ImageView imageViewID){
        imageViewID.setImage(new Image("Giochi1.1.png"));
        System.out.println("button clicked");
    }

    private void updateSlotBooks(ImageView imageViewID){
        imageViewID.setImage(new Image("Libri1.1.png"));
        System.out.println("button clicked");
    }

    private void updateSlotPlants(ImageView imageViewID){
        imageViewID.setImage(new Image("Piante1.1.png"));
        System.out.println("button clicked");
    }

    private void updateSlotTrophies(ImageView imageViewID){
        imageViewID.setImage(new Image("Trofei1.1.png"));
        System.out.println("button clicked");
    }

    public void updateGrid(item[][] grid){
        for(int i=0; i < 9; i++){
            for(int j=0; j < 9; j++){
                if(grid[i][j]==item.CATS){
                    updateSlotCats(SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.GAMES){
                    updateSlotGames(SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.BOOKS){
                    updateSlotBooks(SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.PLANTS){
                    updateSlotPlants(SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.TROPHIES){
                    updateSlotTrophies(SpritesBoard[i][j].fxid);
                }
            }
        }
    }

    private void updateDrawPile(){
        //
    }

    public void putClick(MouseEvent mouseEvent) {
        drawInProgress = false;
    }

    public void drawClick(MouseEvent mouseEvent) {
        drawInProgress = true;
    }

    public void itemClick(MouseEvent mouseEvent) {
        if(drawInProgress && drawCounter == 0){
            DrawPile = new StandardSprite().setDrawPile(t341,t342,t343);
        }
        if(drawInProgress && drawCounter < 3){
            DrawPile[drawCounter].fxid.setImage(((ImageView) mouseEvent.getSource()).getImage());
            drawCounter++;
        }
    }


    public void UpClicked(MouseEvent mouseEvent) {
        Image temp;
        if(drawInProgress){
            if(selectedItem.fxid == DrawPile[1].fxid){
                temp = DrawPile[0].fxid.getImage();
                DrawPile[0].fxid.setImage(selectedItem.getImageView().getImage());
                DrawPile[1].fxid.setImage(temp);
                selectedItem = DrawPile[0];
            }
            if(selectedItem.fxid == DrawPile[2].fxid){
                temp = DrawPile[1].fxid.getImage();
                DrawPile[1].fxid.setImage(selectedItem.getImageView().getImage());
                DrawPile[2].fxid.setImage(temp);
                selectedItem = DrawPile[1];
            }
        }
    }

    public void DownClicked(MouseEvent mouseEvent) {
        Image temp;
        if(drawInProgress){
            if(selectedItem.fxid == DrawPile[1].fxid){
                temp = DrawPile[2].fxid.getImage();
                DrawPile[2].fxid.setImage(selectedItem.getImageView().getImage());
                DrawPile[1].fxid.setImage(temp);
                selectedItem = DrawPile[2];
            }
            if(selectedItem.fxid == DrawPile[0].fxid){
                temp = DrawPile[1].fxid.getImage();
                DrawPile[1].fxid.setImage(selectedItem.getImageView().getImage());
                DrawPile[0].fxid.setImage(temp);
                selectedItem = DrawPile[1];
            }
        }
    }

    public void drawPileClick(MouseEvent mouseEvent) {
        selectedItem = new Sprite((ImageView) mouseEvent.getSource());
    }
}
