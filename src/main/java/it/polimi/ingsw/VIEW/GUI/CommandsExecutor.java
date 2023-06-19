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
                    updateSlotCats(gui.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.GAMES){
                    updateSlotGames(gui.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.BOOKS){
                    updateSlotBooks(gui.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.PLANTS){
                    updateSlotPlants(gui.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.TROPHIES){
                    updateSlotTrophies(gui.SpritesBoard[i][j].fxid);
                }
                if(grid[i][j]==item.FRAMES){
                    updateSlotFrames(gui.SpritesBoard[i][j].fxid);
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
                    gui.mes0.setText("<private : " + gui.privateReceiver + "> " + message.text);
                }else{
                    gui.mes0.setText("<public> " + message.text);
                }
            }
        }
    }

    @Override
    public void chatEnter() {
        if(!gui.privateMessRec && !gui.privateMess){
            if(!gui.stringBuilder.toString().equals("")){

                //CICLO PER SPEZZARE STRINGHE TROPPO LUNGHE SU PIU' RIGHE
                if(((String) gui.stringBuilder.toString()).length() > 50){
                    int x=0;
                    int y=50;
                    while(x+1 < gui.stringBuilder.toString().length()){
                        char[] temp = new char[50];
                        ((String) gui.stringBuilder.toString()).getChars(x,x+y, temp,0);
                        System.out.println(String.valueOf(temp));
                        //
                        MESSAGE m = new MESSAGE();
                        m.text =String.valueOf(temp);
                        //
                        scrollChat(m,false);
                        x+=50;
                        if((gui.stringBuilder.toString().length()-x) <= 50){
                            y = gui.stringBuilder.toString().length()-x;
                        }
                    }
                }else{
                    MESSAGE m = new MESSAGE();
                    m.text =gui.stringBuilder.toString();
                    scrollChat(m,false);
                }
                gui.chatInput.clear();
                gui.stringBuilder = new StringBuilder();
            }
        }
        if(gui.privateMess){
            MESSAGE m = new MESSAGE();
            m.text =gui.privateStringBuilder.toString();
            scrollChat(m, true);
            gui.chatInput.clear();
            gui.privateMess = false;
            gui.privateStringBuilder = new StringBuilder();
            gui.chatInput.setPromptText("type something...");
        }
        if(gui.privateMessRec){
            gui.privateReceiver = gui.privateStringBuilder.toString();
            gui.chatInput.clear();
            gui.privateMessRec = false;
            gui.privateMess = true;
            gui.privateStringBuilder = new StringBuilder();
            gui.chatInput.setPromptText("Insert text for receiver");
        }

    }

    @Override
    public void drawUp() {
        Image temp;
        if(gui.drawInProgress){
            if(gui.selectedItem.fxid == gui.DrawPile[1].fxid){
                temp = gui.DrawPile[0].fxid.getImage();
                gui.DrawPile[0].fxid.setImage(gui.selectedItem.getImageView().getImage());
                gui.DrawPile[1].fxid.setImage(temp);
                gui.selectedItem = gui.DrawPile[0];
            }
            if(gui.selectedItem.fxid == gui.DrawPile[2].fxid){
                temp = gui.DrawPile[1].fxid.getImage();
                gui.DrawPile[1].fxid.setImage(gui.selectedItem.getImageView().getImage());
                gui.DrawPile[2].fxid.setImage(temp);
                gui.selectedItem = gui.DrawPile[1];
            }
        }
    }

    @Override
    public void drawDown() {
        Image temp;
        if(gui.drawInProgress){
            if(gui.selectedItem.fxid == gui.DrawPile[1].fxid){
                temp = gui.DrawPile[2].fxid.getImage();
                gui.DrawPile[2].fxid.setImage(gui.selectedItem.getImageView().getImage());
                gui.DrawPile[1].fxid.setImage(temp);
                gui.selectedItem = gui.DrawPile[2];
            }
            if(gui.selectedItem.fxid == gui.DrawPile[0].fxid){
                temp = gui.DrawPile[1].fxid.getImage();
                gui.DrawPile[1].fxid.setImage(gui.selectedItem.getImageView().getImage());
                gui.DrawPile[0].fxid.setImage(temp);
                gui.selectedItem = gui.DrawPile[1];
            }
        }
    }

    @Override
    public void selectItemToDraw(MouseEvent mouseEvent) {
        if(gui.drawInProgress && gui.drawCounter < 3){
            /**
             * com.draw(controller.username, int row, int col);
             * if(drawValid){
             * gui.DrawPile[gui.drawCounter].fxid.setImage(((ImageView) mouseEvent.getSource()).getImage());
             * gui.drawCounter++;
             * }
            */
            gui.DrawPile[gui.drawCounter].fxid.setImage(((ImageView) mouseEvent.getSource()).getImage());
            gui.drawCounter++;
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
