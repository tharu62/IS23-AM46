module it.polimi.ingsw {

    requires javafx.fxml;
    requires javafx.controls;
    requires com.google.gson;
    requires java.rmi;

    opens it.polimi.ingsw to javafx.fxml;
    exports it.polimi.ingsw;
    opens it.polimi.ingsw.RMI to javafx.base, javafx.fxml, com.google.gson;
    exports it.polimi.ingsw.RMI;
    exports it.polimi.ingsw.TCP;
    opens it.polimi.ingsw.TCP to com.google.gson;
    exports it.polimi.ingsw.TCP.COMANDS;
    exports it.polimi.ingsw.VIEW.GUI;
    opens it.polimi.ingsw.VIEW.GUI to javafx.fxml;
    exports it.polimi.ingsw.MODEL;
    opens it.polimi.ingsw.MODEL to com.google.gson, java.rmi;
    exports it.polimi.ingsw.CONTROLLER_CLIENT_SIDE;
    exports it.polimi.ingsw.VIEW.CLI;
    exports it.polimi.ingsw.CONTROLLER_SERVER_SIDE;

}