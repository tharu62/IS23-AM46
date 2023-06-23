package it.polimi.ingsw.VIEW.GUI;

import it.polimi.ingsw.MODEL.MESSAGE;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ChatData {
    public String privateReceiver;
    public StringBuilder stringBuilder = new StringBuilder();
    public StringBuilder privateStringBuilder = new StringBuilder();
    public boolean privateMess = false, privateMessRec = false;
    public TextField[] chatField = new TextField[7];
    public List<MESSAGE> chatBuffer = new ArrayList<>();
}
