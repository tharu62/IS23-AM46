package it.polimi.ingsw.VIEW.GUI;


import javafx.stage.Stage;

public class PopUpLauncher {
    private Stage window;
    private PopUpController controller;
    private String title, message;

    public PopUpLauncher(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public PopUpLauncher() {
        this("", "");
    }

}
