module it.polimi.ingsw {

    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;
    requires java.rmi;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
    exports it.polimi.ingsw.VIEW;
    opens it.polimi.ingsw.VIEW to javafx.fxml;
    opens it.polimi.ingsw.RMI to javafx.base, javafx.fxml;
    exports it.polimi.ingsw.RMI;
}