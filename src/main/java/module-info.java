module com.nely.gesfond {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;


    opens com.nely.gesfond to javafx.fxml;
    exports com.nely.gesfond;
    exports com.nely.gesfond.View;
    exports  com.nely.gesfond.Model;
    exports com.nely.gesfond.Controller;
    opens com.nely.gesfond.View to javafx.fxml;
}