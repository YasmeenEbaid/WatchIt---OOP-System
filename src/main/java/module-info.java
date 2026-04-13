module watchit.watchit_projectt {
    requires javafx.controls;
    requires javafx.fxml;

    opens watchit.watchit_project to javafx.fxml;
    opens Controllers to javafx.fxml;

    exports watchit.watchit_project;
    exports Controllers;
}
