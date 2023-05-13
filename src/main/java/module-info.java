module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.rmi;
    requires com.google.gson;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw ;
    exports it.polimi.ingsw.GUI;
    opens it.polimi.ingsw.GUI to javafx.fxml;
}