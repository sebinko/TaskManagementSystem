module dk.via.taskmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.desktop;


    opens dk.via.taskmanagement to javafx.fxml;
    opens dk.via.taskmanagement.view to javafx.fxml;

    exports dk.via.taskmanagement;
    exports dk.via.taskmanagement.shared to java.rmi;
    exports dk.via.taskmanagement.view to javafx.fxml;
}