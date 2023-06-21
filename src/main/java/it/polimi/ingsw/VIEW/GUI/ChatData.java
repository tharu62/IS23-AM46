package it.polimi.ingsw.VIEW.GUI;

import javafx.scene.control.TextField;

public class ChatData {
    public String privateReceiver;
    public StringBuilder stringBuilder = new StringBuilder();
    public StringBuilder privateStringBuilder = new StringBuilder();
    public boolean privateMess = false, privateMessRec = false;
    public TextField[] chatField = new TextField[5];
}
