module it.polimi.ingsw {

    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;
    requires java.rmi;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
    opens it.polimi.ingsw.RMI to javafx.base, javafx.fxml;
    exports it.polimi.ingsw.RMI;
    exports it.polimi.ingsw.VIEW.GUI;
    opens it.polimi.ingsw.VIEW.GUI to javafx.fxml;
}