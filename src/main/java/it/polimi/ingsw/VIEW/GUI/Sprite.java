package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Sprite {
    public double x, y;
    public ImageView fxid;

    public Sprite(int x, int y, ImageView fxid) {
        this.x = x;
        this.y = y;
        this.fxid = fxid;
    }

    void updatePos(int x, int y){
        this.fxid.setX(x);
        this.fxid.setY(y);
    }

    /******************************************************************************************************************/

    public Sprite(ImageView fxid){
        this.fxid = fxid;
    }

    public ImageView getImageView() {
        return this.fxid;
    }

    public void setImage(String imageName){
        if(imageName == null){
            this.fxid.setImage(null);
        }else{
            Image img = new Image(imageName);
            this.fxid = new ImageView(img);
        }
    }

}
