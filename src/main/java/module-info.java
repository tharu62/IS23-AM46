module it.polimi.ingsw {
    requires java.rmi;
    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
    exports it.polimi.ingsw.VIEW;
    opens it.polimi.ingsw.VIEW to javafx.fxml;
}