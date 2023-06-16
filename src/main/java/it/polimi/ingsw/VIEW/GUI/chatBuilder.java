package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.control.TextField;

public class chatBuilder{
    public TextField[] standardChat(TextField a, TextField b, TextField c, TextField d, TextField e){
        TextField[] temp = new TextField[5];
        temp[0] = new TextField(a.getId());
        temp[1] = new TextField(b.getId());
        temp[2] = new TextField(c.getId());
        temp[3] = new TextField(d.getId());
        temp[4] = new TextField(e.getId());
        return temp;
    }
}
