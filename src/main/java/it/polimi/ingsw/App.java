package it.polimi.ingsw;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    static SetUp setUpper = new SetUp();

    public static void main(String[] args) throws Exception {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        setUpper.stage = stage;
        setUpper.run();
    }
}
