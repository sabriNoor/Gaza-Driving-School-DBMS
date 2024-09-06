module com.example.dvs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires fontawesomefx;
    requires java.sql;
    requires java.desktop;
    requires jasperreports;

    opens com.example.dvs to javafx.fxml;
    exports com.example.dvs;
}