package it.polimi.ingsw.VIEW.GUI.BOH;


import it.polimi.ingsw.VIEW.GUI.BOH.PopUpController;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

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



    private void init() {
        Pane root = null;
        window = new Stage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("popUp.fxml"));
            root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            controller.setTitle(title);
            controller.setMessage(message);
            controller.setOwnStage(window);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Scene s = new Scene(root, 412, 257);
        controller.forceInitialize();
        window.setScene(s);
        window.setTitle(this.title);
    }

    public void show() {
        Platform.runLater(() -> {
            init();
            window.setAlwaysOnTop(true);
            window.setResizable(false);
            window.initModality(Modality.APPLICATION_MODAL);
            window.showAndWait();
        });
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void close() {
        window.close();
    }
}
