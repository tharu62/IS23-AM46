package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.GAME;
import it.polimi.ingsw.MODEL.item;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI extends Application {
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
    public boolean privateMess = false;
    public boolean privateMessRec = false;
    public String privateReceiver;
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder privateStringBuilder = new StringBuilder();
    public Sprite selectedItem = new Sprite(null);
    public Sprite[][] SpritesBoard;
    public Sprite[] DrawPile = new Sprite[3];
    public TextField[] chatField = new TextField[5];
    public Sprite[][] SpriteBookshelf = new Sprite[6][5];

    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(GUI.class.getResource("/it.polimi.ingsw/BOH/AppWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MY SHELFIE GAME");
        stage.setScene(scene);
        stage.show();
    }

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
    }

    private void updateSlotGames(ImageView imageViewID){
        imageViewID.setImage(new Image("Giochi1.1.png"));
    }

    private void updateSlotBooks(ImageView imageViewID){
        imageViewID.setImage(new Image("Libri1.1.png"));
    }

    private void updateSlotPlants(ImageView imageViewID){
        imageViewID.setImage(new Image("Piante1.1.png"));
    }

    private void updateSlotTrophies(ImageView imageViewID){
        imageViewID.setImage(new Image("Trofei1.1.png"));
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

    synchronized public void scrollChat(String text, boolean Private){
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
                if(Private){
                    mes0.setText("<private : " + privateReceiver + "> " + text);
                }else{
                    mes0.setText("<public> " + text);
                }
            }
        }
    }

    public void chatEnter(MouseEvent mouseEvent) {
        if(!privateMessRec && !privateMess){
            if(!stringBuilder.toString().equals("")){

                //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
                if(((String) stringBuilder.toString()).length() > 50){
                    int x=0;
                    int y=50;
                    while(x+1 < stringBuilder.toString().length()){
                        char[] temp = new char[50];
                        ((String) stringBuilder.toString()).getChars(x,x+y, temp,0);
                        System.out.println(String.valueOf(temp));
                        scrollChat(String.valueOf(temp),false);
                        x+=50;
                        if((stringBuilder.toString().length()-x) <= 50){
                            y = stringBuilder.toString().length()-x;
                        }
                    }
                }else{
                    scrollChat(stringBuilder.toString(),false);
                }
                chatInput.clear();
                stringBuilder = new StringBuilder();
            }
        }
        if(privateMess){
            scrollChat(privateStringBuilder.toString(), true);
            chatInput.clear();
            privateMess = false;
            privateStringBuilder = new StringBuilder();
            chatInput.setPromptText("type something...");
        }
        if(privateMessRec){
            privateReceiver = privateStringBuilder.toString();
            chatInput.clear();
            privateMessRec = false;
            privateMess = true;
            privateStringBuilder = new StringBuilder();
            chatInput.setPromptText("Insert text for receiver");
        }

    }

    public void privateChatEnter(MouseEvent mouseEvent) {
        chatInput.setPromptText("Insert receiver");
        privateMessRec = true;
    }

    public void setScene(MouseEvent mouseEvent) {
        this.SpritesBoard = new StandardSprite().setBoard(gridPane);
        GAME game = new GAME();
        game.space.board.setGrid(4);
        updateGrid(game.space.board.Grid);
    }
}

