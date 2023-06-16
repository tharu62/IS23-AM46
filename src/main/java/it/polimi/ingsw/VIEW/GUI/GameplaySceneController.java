package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.item;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.TAB;

public class GameplaySceneController {
    @FXML
    public GridPane gridPane;
    @FXML
    public ImageView t341;
    @FXML
    public ImageView t342;
    @FXML
    public ImageView t343;
    @FXML
    public TextField mes0;
    @FXML
    public TextField mes1;
    @FXML
    public TextField mes2;
    @FXML
    public TextField mes3;
    @FXML
    public TextField mes4;
    @FXML
    public TextField chatInput;
    int i=0;
    public boolean drawInProgress = false;
    public int drawCounter = 0;
    public boolean putInProgress = false;
    String playerInput = new String();
    public boolean pce = false;
    public boolean privateMess = false;
    public boolean privateMessRec = false;
    public String privateReceiver;
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder privateStringBuilder = new StringBuilder();
    public Sprite selectedItem = new Sprite(null);
    public Sprite[][] SpritesBoard = new StandardSprite().setBoard(gridPane);
    public Sprite[] DrawPile = new Sprite[3];
    public TextField[] chatField = new TextField[5];
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

    public void inputKey(KeyEvent keyEvent) {
            if(pce && !privateMessRec ){
                chatInput.setPromptText("type the name of the receiver for the private message");
                privateMessRec = true;
            }
            if(privateMessRec || privateMess){
                privateStringBuilder.append(keyEvent.getCharacter());
            }
            else{
                stringBuilder.append(keyEvent.getCharacter());
            }
    }

    public void chatClick(MouseEvent mouseEvent) {
        chatField = new chatBuilder().standardChat(mes0,mes1,mes2,mes3,mes4);
    }

    synchronized public void scrollChat(String text){
        for(int i=4;i>0;i--){
            if(i==4){
                mes4.setText(mes3.getText());
            }
            if(i==3){
                mes3.setText(mes2.getText());
            }
            if(i==2){
                mes2.setText(mes1.getText());
            }
            if(i==1){
                mes1.setText(mes0.getText());
                if(privateMess){
                    mes0.setText("<private:" + privateReceiver + "> " + text);
                    privateMess = false;
                }
                mes0.setText("<public> " + text);
            }
        }
    }

    public void chatEnter(MouseEvent mouseEvent) {
        if(!privateMessRec && !privateMess){
            scrollChat(stringBuilder.toString());
            chatInput.clear();
            stringBuilder = new StringBuilder();
        }
        if(privateMess){
            scrollChat(privateStringBuilder.toString());
            chatInput.clear();
            privateStringBuilder = new StringBuilder();
        }
        if(privateMessRec){
            privateReceiver = privateStringBuilder.toString();
            chatInput.clear();
            privateMessRec = false;
            privateMess = true;
            privateStringBuilder = new StringBuilder();
        }

    }

    public void privateChatEnter(MouseEvent mouseEvent) {
        pce = true;
    }
}
