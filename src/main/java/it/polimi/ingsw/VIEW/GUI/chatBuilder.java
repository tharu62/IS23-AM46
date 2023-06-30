package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.control.TextField;

public class chatBuilder{

    /**
     * This method instantiate and initialize an array of TexFields with the given TexFields as parameter.
     *
     * @return an array of TextField fully initialized. ( TexField[7] )
     */
    public TextField[] standardChat(TextField a, TextField b, TextField c, TextField d, TextField e, TextField f, TextField g){
        TextField[] temp = new TextField[7];
        temp[0] = new TextField(a.getId());
        temp[1] = new TextField(b.getId());
        temp[2] = new TextField(c.getId());
        temp[3] = new TextField(d.getId());
        temp[4] = new TextField(e.getId());
        temp[5] = new TextField(f.getId());
        temp[6] = new TextField(g.getId());
        return temp;
    }
}
