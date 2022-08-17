module com.bowhero22.mpplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.media;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jdk.unsupported;

    opens com.bowhero22.mpplayer to javafx.fxml;
    exports com.bowhero22.mpplayer;
    exports com.bowhero22.mpplayer.controllers;
    opens com.bowhero22.mpplayer.controllers to javafx.fxml;
}