package it.polimi.ingsw.VIEW.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GUIcontroller {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}