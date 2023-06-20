package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CONTROLLER;
import it.polimi.ingsw.CONTROLLER_CLIENT_SIDE.CommunicationProtocol;
import it.polimi.ingsw.MODEL.MESSAGE;
import it.polimi.ingsw.MODEL.item;
import it.polimi.ingsw.RMI.ClientRMI;
import it.polimi.ingsw.TCP.ClientTCP;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class CommandsExecutor implements GUI_commands {
    CommunicationProtocol com;
    GUI gui;
    CONTROLLER controller;

    public CommandsExecutor( CONTROLLER controller, ClientRMI client, GUI gui) {
    this.com = new RMI(client);
    this.gui = gui;
    this.controller = controller;
    }

    public CommandsExecutor( CONTROLLER controller, ClientTCP client, GUI gui) {
        this.com = new TCP(client);
        this.gui = gui;
        this.controller = controller;
    }


    @Override
    public void updateGrid(item[][] grid) {
        for(int i=0; i < 9; i++){
            for(int j=0; j < 9; j++){
                if(grid[i][j]== item.CATS){
                    updateSlotCats(GUI.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.GAMES){
                    updateSlotGames(GUI.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.BOOKS){
                    updateSlotBooks(GUI.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.PLANTS){
                    updateSlotPlants(GUI.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.TROPHIES){
                    updateSlotTrophies(GUI.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.FRAMES){
                    updateSlotFrames(GUI.SpritesBoard[i][j].fxid);
                }
            }
        }
    }

    @Override
    public void updateBookshalf(item[][] grid) {

    }

    @Override
    public void scrollChat(MESSAGE message, boolean Private) {
        for(int i=4;i>0;i--){
            if(i==4){
                gui.mes4.setText(gui.mes3.getText());
            }
            if(i==3){
                gui.mes3.setText(gui.mes2.getText());
            }
            if(i==2){
                gui.mes2.setText(gui.mes1.getText());
            }
            if(i==1){
                gui.mes1.setText(gui.mes0.getText());
                if(Private){
                    gui.mes0.setText("<private : " + GUI.chatData.privateReceiver + "> " + message.text);
                }else{
                    gui.mes0.setText("<public> " + message.text);
                }
            }
        }
    }

    @Override
    public void chatEnter() {
        if(!GUI.chatData.privateMessRec && !GUI.chatData.privateMess){
            if(!GUI.chatData.stringBuilder.toString().equals("")){

                //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
                if(((String) GUI.chatData.stringBuilder.toString()).length() > 50){
                    int x=0;
                    int y=50;
                    while(x+1 < GUI.chatData.stringBuilder.toString().length()){
                        char[] temp = new char[50];
                        ((String) GUI.chatData.stringBuilder.toString()).getChars(x,x+y, temp,0);
                        System.out.println(String.valueOf(temp));
                        //
                        MESSAGE m = new MESSAGE();
                        m.text =String.valueOf(temp);
                        //
                        scrollChat(m,false);
                        x+=50;
                        if((GUI.chatData.stringBuilder.toString().length()-x) <= 50){
                            y = GUI.chatData.stringBuilder.toString().length()-x;
                        }
                    }
                }else{
                    MESSAGE m = new MESSAGE();
                    m.text =GUI.chatData.stringBuilder.toString();
                    scrollChat(m,false);
                }
                gui.chatInput.clear();
                GUI.chatData.stringBuilder = new StringBuilder();
            }
        }
        if(GUI.chatData.privateMess){
            MESSAGE m = new MESSAGE();
            m.text =GUI.chatData.privateStringBuilder.toString();
            scrollChat(m, true);
            gui.chatInput.clear();
            GUI.chatData.privateMess = false;
            GUI.chatData.privateStringBuilder = new StringBuilder();
            gui.chatInput.setPromptText("type something...");
        }
        if(GUI.chatData.privateMessRec){
            GUI.chatData.privateReceiver = GUI.chatData.privateStringBuilder.toString();
            gui.chatInput.clear();
            GUI.chatData.privateMessRec = false;
            GUI.chatData.privateMess = true;
            GUI.chatData.privateStringBuilder = new StringBuilder();
            gui.chatInput.setPromptText("Insert text for receiver");
        }

    }

    @Override
    public void drawUp() {
        Image temp;
        if(GUI.drawInProgress){
            if(GUI.selectedItem.fxid == GUI.DrawPile[1].fxid){
                temp = GUI.DrawPile[0].fxid.getImage();
                GUI.DrawPile[0].fxid.setImage(GUI.selectedItem.getImageView().getImage());
                GUI.DrawPile[1].fxid.setImage(temp);
                GUI.selectedItem = GUI.DrawPile[0];
            }
            if(GUI.selectedItem.fxid == GUI.DrawPile[2].fxid){
                temp = GUI.DrawPile[1].fxid.getImage();
                GUI.DrawPile[1].fxid.setImage(GUI.selectedItem.getImageView().getImage());
                GUI.DrawPile[2].fxid.setImage(temp);
                GUI.selectedItem = GUI.DrawPile[1];
            }
        }
    }

    @Override
    public void drawDown() {
        Image temp;
        if(GUI.drawInProgress){
            if(GUI.selectedItem.fxid == GUI.DrawPile[1].fxid){
                temp = GUI.DrawPile[2].fxid.getImage();
                GUI.DrawPile[2].fxid.setImage(GUI.selectedItem.getImageView().getImage());
                GUI.DrawPile[1].fxid.setImage(temp);
                GUI.selectedItem = GUI.DrawPile[2];
            }
            if(GUI.selectedItem.fxid == GUI.DrawPile[0].fxid){
                temp = GUI.DrawPile[1].fxid.getImage();
                GUI.DrawPile[1].fxid.setImage(GUI.selectedItem.getImageView().getImage());
                GUI.DrawPile[0].fxid.setImage(temp);
                GUI.selectedItem = GUI.DrawPile[1];
            }
        }
    }

    @Override
    public void selectItemToDraw(MouseEvent mouseEvent) {
        if(GUI.drawInProgress && GUI.drawCounter < 3){
            /**
             * com.draw(controller.username, int row, int col);
             * if(drawValid){
             * gui.DrawPile[gui.drawCounter].fxid.setImage(((ImageView) mouseEvent.getSource()).getImage());
             * gui.drawCounter++;
             * }
            */
            GUI.DrawPile[GUI.drawCounter].fxid.setImage(((ImageView) mouseEvent.getSource()).getImage());
            GUI.drawCounter++;
        }
    }

    @Override
    public void putDrawInBookshelf() {
        /**
         * com.put(controller.username, int col, int a, int b, int c);
         * if(putValid){
         *      updateBookshelf(item[6][5]);
         *      com.endTurn();
         * }
         */
    }

    @Override
    public void setCommonGoals() {

    }

    @Override
    public void setPersonalGoal() {

    }

    @Override
    public void login() {

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

    private void updateSlotFrames(ImageView imageViewID){
        imageViewID.setImage(new Image("Cornici1.1.png"));
    }
}
