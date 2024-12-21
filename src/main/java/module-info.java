module org.smartbank.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens org.smartbank.client.controller to javafx.fxml;
    opens org.smartbank.client to javafx.fxml;
    exports org.smartbank.client;
}